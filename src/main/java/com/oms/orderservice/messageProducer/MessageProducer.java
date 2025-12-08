package com.oms.orderservice.messageProducer;

import com.oms.orderservice.config.KafkaConfigurations;
import com.oms.orderservice.dto.PaymentRequestDTO;
import com.oms.orderservice.events.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageProducer {
    private KafkaTemplate<String,Object> template;
    public MessageProducer(KafkaTemplate<String, Object> template) {
        this.template = template;
    }
    public void publishPaymentEvent(PaymentRequestDTO event){
        log.info("Publishing payment event {} ", event.toString());
        PaymentEvent orderEvent = new PaymentEvent(event);
        template.send(KafkaConfigurations.OB_PAYMENT_EVENTS_TOPIC, orderEvent);
    }
}
