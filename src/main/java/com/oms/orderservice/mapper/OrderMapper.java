package com.oms.orderservice.mapper;

import com.oms.orderservice.dto.UpdateOrderRequestDTO;
import com.oms.orderservice.entity.PurchaseOrder;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOrderFromDto(UpdateOrderRequestDTO dto, @MappingTarget PurchaseOrder entity);
}