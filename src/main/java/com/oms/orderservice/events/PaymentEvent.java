package com.oms.orderservice.events;

import com.oms.orderservice.dto.PaymentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PaymentEvent {
    private PaymentRequestDTO paymentRequest;
}
