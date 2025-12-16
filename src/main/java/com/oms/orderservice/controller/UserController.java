package com.oms.orderservice.controller;

import com.oms.orderservice.commondto.UserRequestDTO;
import com.oms.orderservice.commondto.UserResponseDTO;
import com.oms.orderservice.dto.UpdateOrderRequestDTO;
import com.oms.orderservice.dto.UpdateUserRequestDTO;
import com.oms.orderservice.entity.Users;
import com.oms.orderservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserService userService;
    @PostMapping("/save")
    public UserResponseDTO saveUser(@RequestBody UserRequestDTO userRequestDTO){
        final String methodName = "saveUser";
        logger.info("Entry {}", methodName);
        UserResponseDTO userResponse = userService.createUser(userRequestDTO);
        logger.info("Exit {}", methodName);
        return userResponse;
    }
    @GetMapping("/userId/{userId}")
    public Users getUserByUserId(@PathVariable String userId){
        Users userResponse = userService.getUserById(userId);
        return userResponse;
    }

    @PutMapping("/update")
    public Users updateUser(@RequestBody UpdateUserRequestDTO updateOrderRequest){
        final String methodName = "updateUser";
        logger.info(methodName, "{} Entry","Update User Incoming Payload {}", updateOrderRequest);
        Users user = userService.updateUser(updateOrderRequest);

        logger.info("Exit {}", methodName);
        return user;
    }
}
