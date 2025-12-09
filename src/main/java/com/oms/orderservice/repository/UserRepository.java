package com.oms.orderservice.repository;

import com.oms.orderservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users getUserByUserId(String userId);
}
