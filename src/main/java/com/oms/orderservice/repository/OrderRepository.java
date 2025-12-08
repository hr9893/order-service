package com.oms.orderservice.repository;

import com.oms.orderservice.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder,Integer> {
    public PurchaseOrder getOrderByOrderId(String orderId);
}
