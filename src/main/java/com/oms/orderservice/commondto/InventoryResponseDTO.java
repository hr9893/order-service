package com.oms.orderservice.commondto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponseDTO {
    private Integer itemQuantity;
    private Integer itemId;
    private String itemDescription;
    private boolean itemInStock;
    private double unitPrice;
}
