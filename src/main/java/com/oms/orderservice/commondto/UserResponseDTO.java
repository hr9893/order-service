package com.oms.orderservice.commondto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String userCreatedMessage;
    private LocalDateTime createdAt = LocalDateTime.now();
}
