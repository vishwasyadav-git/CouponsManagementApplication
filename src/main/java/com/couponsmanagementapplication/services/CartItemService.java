package com.couponsmanagementapplication.services;


import com.couponsmanagementapplication.models.CartItem;
import com.couponsmanagementapplication.repositories.CartItemRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    public void deleteCartItem(Long id) {
        cartItemRepository.deleteById(id);
    }
}

