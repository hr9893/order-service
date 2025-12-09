package com.oms.orderservice.commondto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String userId;
    private String firstName;
    private String lastName;
    private Character gender;
    private String emailId;
    private String password;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String addressLine1;
    private String city;
    private String country;
}
