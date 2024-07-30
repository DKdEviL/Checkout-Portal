package com.example.checkoutportal.data.repository;

import com.example.checkoutportal.data.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
