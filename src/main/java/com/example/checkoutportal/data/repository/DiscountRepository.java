package com.example.checkoutportal.data.repository;

import com.example.checkoutportal.data.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    Discount findByType(String type);
}
