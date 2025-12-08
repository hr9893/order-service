package com.oms.orderservice.events;

import com.oms.orderservice.commondto.InventoryRequestDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Data
public class InventoryEvent {
    private UUID eventId = UUID.randomUUID();
    private Date eventDate =new Date();
    private InventoryRequestDTO inventoryRequestDTO;
    private InventoryStatus inventoryStatus;

    public InventoryEvent(InventoryRequestDTO inventoryRequestDTO, InventoryStatus inventoryStatus) {
        this.inventoryRequestDTO = inventoryRequestDTO;
        this.inventoryStatus = inventoryStatus;
    }

    public InventoryEvent(InventoryRequestDTO inventoryRequestDTO) {
        this.inventoryRequestDTO = inventoryRequestDTO;
    }

}

