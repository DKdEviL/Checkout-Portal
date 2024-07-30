package com.example.checkoutportal.data.repository;

import com.example.checkoutportal.data.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
