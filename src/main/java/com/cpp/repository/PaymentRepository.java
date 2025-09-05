package com.cpp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccp.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
