package com.oms.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequestDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailId;
    private String password;
    private Character gender;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String addressLine1;
    private String city;
    private String country;

}
