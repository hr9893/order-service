package com.oms.orderservice.dto;

import com.oms.orderservice.events.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private String userId;
    private String orderId;
    private long transactionId;
    private double orderTotal;
    private String eventTimestamp;
    private PaymentStatus paymentStatus;
}
