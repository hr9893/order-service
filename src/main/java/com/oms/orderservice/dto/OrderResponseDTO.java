package com.oms.orderservice.dto;

import com.oms.orderservice.events.OrderStatus;
import com.oms.orderservice.events.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private String userId;
    private Integer itemId;
    private String itemDescription;
    private double amount;
    private double orderTotal;
    private Integer quantity;
    private String orderId;
    private OrderStatus orderstatus;
    private PaymentStatus paymentStatus;
}
