package com.oms.orderservice.controller;

import com.oms.orderservice.dto.OrderRequestDTO;
import com.oms.orderservice.dto.OrderResponseDTO;
import com.oms.orderservice.dto.UpdateOrderRequestDTO;
import com.oms.orderservice.entity.PurchaseOrder;

import com.oms.orderservice.services.OrderService;
import com.oms.orderservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;

    @PostMapping("/save")
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        logger.info("Incoming request for Order Creation : {}",orderRequestDTO.toString());

        if (userService.validateUser(orderRequestDTO.getUserId())) {
            OrderResponseDTO saveOrderResponse = orderService.createOrder(orderRequestDTO);

            return saveOrderResponse;
        } else
            return null;
    }
    @GetMapping("/getAll")
    public List<PurchaseOrder> getAllOrders(){
        return orderService.getAllPurchaseOrders();
    }
    @GetMapping("/orderId/{orderId}")
    public PurchaseOrder getOrderByOrderId(@PathVariable String orderId){
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/update")
    public PurchaseOrder updateOrder(@RequestBody UpdateOrderRequestDTO updateOrderRequestDTO){
        final String methodName = "updateOrder";
        logger.info(methodName, "{} Entry", "Incoming Request for Order Update {}", updateOrderRequestDTO);

        return orderService.updateOrders(updateOrderRequestDTO);
    }
}
