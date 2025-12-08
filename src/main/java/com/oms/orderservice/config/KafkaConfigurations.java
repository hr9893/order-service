package com.oms.orderservice.config;

public class KafkaConfigurations {
    public static final String OB_PAYMENT_EVENTS_TOPIC = "payment-event";
    public static final String OB_PAYMENT_EVENT_GROUP = "payment-event-group";
    public static final String IB_SUCCESS_PAYMENT_EVENT = "payment-success-event";
    public static final String IB_SUCCESS_PAYMENT_GROUP = "ib-payment-group";
    public static final String IB_PAYMENT_FAILURE_EVENT = "payment-failure-event";
    public static final String IB_PAYMENT_FAILURE_GROUP = "ib-payment-failure-group";
}
