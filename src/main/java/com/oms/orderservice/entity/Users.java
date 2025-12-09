package com.oms.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table(name = "USERS")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    @Column(name = "USER_ID", unique = true, nullable = false)
    @Id
    private String userId;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "EMAIL_ID", unique = true, nullable = false)
    private String emailId;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "GENDER")
    private Character gender;
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;
    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
    @Column(name = "IS_ACTIVE")
    @Builder.Default
    private boolean isActive = true;
    @Column(name = "ADDRESS_LINE1")
    private String addressLine1;
    @Column(name = "CITY")
    private String city;
    @Column(name = "COUNTRY")
    private String country;
    @Column(name = "CREATED_AT")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "UPDATED_AT")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
