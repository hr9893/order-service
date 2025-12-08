package com.oms.orderservice.messageListner;

import com.oms.orderservice.events.PaymentEvent;
import com.oms.orderservice.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.oms.orderservice.config.KafkaConfigurations.*;

@Component
@Slf4j
public class MessageListner {
    private static final Logger logger = LoggerFactory.getLogger(MessageListner.class);
    @Autowired
    OrderService orderService;
    @KafkaListener(topics = IB_SUCCESS_PAYMENT_EVENT, groupId = IB_SUCCESS_PAYMENT_GROUP)
    public void paymentSuccessEventListener(PaymentEvent paymentResponse){
        logger.info("paymentSuccessEventListener Entry {}",paymentResponse.toString());
        orderService.updateOrderWithPayment(paymentResponse);
    }
    @KafkaListener(topics = IB_PAYMENT_FAILURE_EVENT, groupId = IB_PAYMENT_FAILURE_GROUP)
    public void paymentFailureEventListener(PaymentEvent paymentfailureResponse){
        logger.info("paymentFailureEventListener Entry {}", paymentfailureResponse.toString());
        orderService.updateOrderWithPayment(paymentfailureResponse);
    }
}
