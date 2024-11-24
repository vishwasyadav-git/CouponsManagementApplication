package com.couponsmanagementapplication.repositories;

import com.couponsmanagementapplication.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name); // Find a product by name
}
