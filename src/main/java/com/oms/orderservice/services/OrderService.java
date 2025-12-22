package com.oms.orderservice.services;

import com.oms.orderservice.commondto.InventoryRequestDTO;
import com.oms.orderservice.commondto.InventoryResponseDTO;
import com.oms.orderservice.dto.*;
import com.oms.orderservice.entity.PurchaseOrder;
import com.oms.orderservice.events.OrderStatus;
import com.oms.orderservice.events.PaymentEvent;
import com.oms.orderservice.events.PaymentStatus;
import com.oms.orderservice.mapper.OrderMapper;
import com.oms.orderservice.messageProducer.MessageProducer;
import com.oms.orderservice.repository.OrderRepository;
import com.oms.orderservice.restService.InventoryRestClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MessageProducer eventsProducer;
    @Autowired
    private InventoryRestClient restClient;
    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        final String methodName = "createOrder";
        log.info("Entry", methodName);

        InventoryRequestDTO inventoryRequest = new InventoryRequestDTO(orderRequestDTO.getItemQuantity(), orderRequestDTO.getItemId());
        InventoryResponseDTO inventoryResponse = restClient.checkInventory(inventoryRequest).getBody();

        PurchaseOrder savedOrder = convertDtoToPurchaseOrder(orderRequestDTO, inventoryResponse);
        savedOrder = orderRepository.save(savedOrder);

        OrderResponseDTO savedOrderResponse = buildOrderResponse(orderRequestDTO, savedOrder.getOrderTotal(),
                savedOrder.getOrderId(), inventoryResponse);

        publishPaymentEvent(savedOrderResponse.getOrderId());

        log.info("Order Saved Successfully : {}", savedOrderResponse.toString());
        log.info("Exit", methodName);

        return savedOrderResponse;
    }

    private OrderResponseDTO buildOrderResponse(OrderRequestDTO orderRequestDTO, double orderTotal, String orderId, InventoryResponseDTO inventoryResponse) {

        OrderResponseDTO savedOrderResponse = convertDtoToEntity(orderRequestDTO);

        savedOrderResponse.setOrderTotal(orderTotal);
        savedOrderResponse.setOrderstatus(OrderStatus.ORDER_CONFIRMED);
        savedOrderResponse.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);
        savedOrderResponse.setOrderId(orderId);
        savedOrderResponse.setItemDescription(inventoryResponse.getItemDescription());
        if (!inventoryResponse.isItemInStock()) {
            savedOrderResponse.setOrderstatus(OrderStatus.ORDER_CANCELLED);
        }

        return savedOrderResponse;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return orderRepository.findAll();
    }

    private OrderResponseDTO convertDtoToEntity(OrderRequestDTO dto) {
        OrderResponseDTO Order = new OrderResponseDTO();

        Order.setUserId(dto.getUserId());
        Order.setQuantity(dto.getItemQuantity());
        Order.setItemId(dto.getItemId());

        return Order;
    }

    private PurchaseOrder convertDtoToPurchaseOrder(OrderRequestDTO orderRequestDTO, InventoryResponseDTO inventoryResponse) {
        PurchaseOrder Order = new PurchaseOrder();

        Order.setOrderId(generateOrderId());
        Order.setUserId(orderRequestDTO.getUserId());
        Order.setItemQuantity(orderRequestDTO.getItemQuantity());
        Order.setItemId(orderRequestDTO.getItemId());
        Order.setCreatedTimestamp(getLocalDateTime());
        Order.setLastUpdatedTimestamp(getLocalDateTime());
        Order.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);
        Order.setPrice(inventoryResponse.getUnitPrice());
        Order.setItemDescription(inventoryResponse.getItemDescription());
        Order.setOrderTotal(inventoryResponse.getUnitPrice() * orderRequestDTO.getItemQuantity());

        return Order;
    }

    public String generateOrderId() {
        long timestamp = System.currentTimeMillis();
        int random = ThreadLocalRandom.current().nextInt(100000, 999999);

        return "ORD-" + timestamp + "-" + random;
    }

    @Transactional
    public void updateOrderWithPayment(PaymentEvent paymentResponse) {
        logger.info("Updating Payment with Payment Event : {}", paymentResponse.toString());

        PurchaseOrder getOrder = orderRepository.getOrderByOrderId(paymentResponse.getPaymentRequest().getOrderId());
        getOrder.setPaymentTransactionId(paymentResponse.getPaymentRequest().getTransactionId());
        getOrder.setLastUpdatedTimestamp(getLocalDateTime());

        if (paymentResponse.getPaymentRequest().getPaymentStatus().equals(PaymentStatus.COMPLETED)) {
            getOrder.setOrderStatus(OrderStatus.ORDER_FULFILLED);
            getOrder.setPaymentStatus(PaymentStatus.PAYMENT_COMPLETED);

        } else if (paymentResponse.getPaymentRequest().getPaymentStatus().equals(PaymentStatus.FAILED)) {
            getOrder.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
            getOrder.setOrderStatus(OrderStatus.ORDER_CANCELLED);
            getOrder.setPaymentFailureReason("Insufficient Balance");
        }
        orderRepository.save(getOrder);
    }

    private void publishPaymentEvent(String orderId) {

        PurchaseOrder getOrderResponse = orderRepository.getOrderByOrderId(orderId);

        logger.info("Preparing Payment Publish Event", getOrderResponse.toString());
        PaymentRequestDTO paymentEvent = new PaymentRequestDTO();

        paymentEvent.setUserId(getOrderResponse.getUserId());
        paymentEvent.setTransactionId(generateNumericTransactionId());
        paymentEvent.setOrderId(getOrderResponse.getOrderId());
        paymentEvent.setOrderTotal(getOrderResponse.getOrderTotal());
        paymentEvent.setEventTimestamp(getLocalDateTime());
        paymentEvent.setPaymentStatus(PaymentStatus.AWAITING_PAYMENT);

        eventsProducer.publishPaymentEvent(paymentEvent);
    }

    @Transactional
    public PurchaseOrder updateOrders(UpdateOrderRequestDTO orderRequestDTO) {
        final String methodName = "updateOrders";
        logger.info(methodName, "{} Entry");
        try {
            PurchaseOrder getOrder = orderRepository.getOrderByOrderId(orderRequestDTO.getOrderId());
            if (getOrder != null) {
                orderMapper.updateOrderFromDto(orderRequestDTO, getOrder);

                logger.info(methodName, "{} Exit", "Outgoing Payload {}", getOrder);
                return orderRepository.save(getOrder);
            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while updating the Order", e);
        }
        return null;
    }
    public PurchaseOrder getOrderById(String orderId) {
        PurchaseOrder getOrderResponse = orderRepository.getOrderByOrderId(orderId);
        return getOrderResponse;
    }

    public String getLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        return formattedDateTime;
    }

    public static long generateNumericTransactionId() {
        UUID uuid = UUID.randomUUID();
        long id = uuid.getMostSignificantBits() & Long.MAX_VALUE;
        return id;
    }

}
