package com.oms.orderservice.controller;

import com.oms.orderservice.dto.OrderRequestDTO;
import com.oms.orderservice.dto.OrderResponseDTO;
import com.oms.orderservice.entity.PurchaseOrder;

import com.oms.orderservice.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @PostMapping("/save")
    public OrderResponseDTO createOrder(@RequestBody OrderRequestDTO orderRequestDTO)
    {
        OrderResponseDTO saveOrderResponse = orderService.createOrder(orderRequestDTO);

        return saveOrderResponse;
    }
    @GetMapping("/getAll")
    public List<PurchaseOrder> getAllOrders(){
        return orderService.getAllPurchaseOrders();
    }
    @GetMapping("/orderId/{orderId}")
    public PurchaseOrder getOrderByOrderId(@PathVariable String orderId){
        return orderService.getOrderById(orderId);
    }
}
