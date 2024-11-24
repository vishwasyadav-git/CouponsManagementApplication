package com.couponsmanagementapplication.services;

import com.couponsmanagementapplication.models.Cart;
import com.couponsmanagementapplication.models.CartItem;
import com.couponsmanagementapplication.repositories.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public Cart getCartById(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public void deleteCart(Long id) {
        cartRepository.deleteById(id);
    }

    public void addItemToCart(Cart cart, CartItem item) {
        cart.getItems().add(item);
        cartRepository.save(cart);
    }
}

