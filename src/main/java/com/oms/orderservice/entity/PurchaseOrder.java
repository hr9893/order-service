package com.oms.orderservice.entity;

import com.oms.orderservice.events.OrderStatus;
import com.oms.orderservice.events.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "PURCHASE_ORDERS")
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrder {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "PK")
    private Long pk;
    @Column(name = "ORDER_ID")
    private String orderId;
    @Column(name = "ITEM_ID")
    private Integer itemId;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "ITEM_PRICE")
    private double price;
    @Column(name = "ORDER_TOTAL")
    private double orderTotal;
    @Column(name = "ITEM_QUANTITY")
    private Integer itemQuantity;
    @Column(name = "PAYMENT_FAILURE_REASON")
    private String paymentFailureReason;
    @Column(name = "PAYMENT_TRANSACTION_ID")
    private Long paymentTransactionId;
    @Column(name = "CREATED_TIMESTAMP")
    private String createdTimestamp;
    @Column(name = "LAST_UPDATED_TIMESTAMP")
    private String lastUpdatedTimestamp;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
