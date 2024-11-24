package com.couponsmanagementapplication.repositories;

import com.couponsmanagementapplication.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
