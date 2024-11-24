package com.couponsmanagementapplication.repositories;

import com.couponsmanagementapplication.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
