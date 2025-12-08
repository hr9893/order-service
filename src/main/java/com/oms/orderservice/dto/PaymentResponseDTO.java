package com.oms.orderservice.dto;

import com.oms.orderservice.events.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    String orderId;
    Long paymentTransactionId;
    PaymentStatus paymentStatus;
    String paymentFailureReason;
}
