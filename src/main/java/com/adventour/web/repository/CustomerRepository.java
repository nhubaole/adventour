package com.adventour.web.repository;

import com.adventour.web.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);
    Optional<Customer> findByNameCustomer(String name);
    Optional<Customer> findByPhoneNumber(String phone);
    Optional<Customer> findByEmail(String email);
    long count();

}