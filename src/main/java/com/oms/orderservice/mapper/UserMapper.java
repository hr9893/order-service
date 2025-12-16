package com.oms.orderservice.mapper;

import com.oms.orderservice.dto.UpdateUserRequestDTO;
import com.oms.orderservice.entity.Users;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserMapper(UpdateUserRequestDTO updateUser, @MappingTarget Users user);
}
