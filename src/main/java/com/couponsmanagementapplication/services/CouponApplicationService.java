package com.couponsmanagementapplication.services;


import com.couponsmanagementapplication.models.Cart;
import com.couponsmanagementapplication.models.Coupon;
import com.couponsmanagementapplication.models.CouponApplication;
import com.couponsmanagementapplication.repositories.CartRepository;
import com.couponsmanagementapplication.repositories.CouponApplicationRepository;
import com.couponsmanagementapplication.repositories.CouponRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponApplicationService {

    private final CouponApplicationRepository couponApplicationRepository;
    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;

    public CouponApplicationService(CouponApplicationRepository couponApplicationRepository, CouponRepository couponRepository, CartRepository cartRepository) {
        this.couponApplicationRepository = couponApplicationRepository;
        this.couponRepository = couponRepository;
        this.cartRepository = cartRepository;
    }

    public CouponApplication applyCoupon(Coupon coupon, Cart cart, double discount) {
        CouponApplication application = new CouponApplication();
        application.setCoupon(coupon);
        application.setCart(cart);
        application.setDiscountApplied(discount);
        return couponApplicationRepository.save(application);
    }

    public List<CouponApplication> getApplicationsForCart(Long cartId) {
        return couponApplicationRepository.findAll(); // Extend logic for filtering by cart
    }

    public CouponApplication applyCoupon(Long couponId, Long cartId) {
        // Step 1: Fetch the coupon by ID
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new RuntimeException("Coupon not found with ID: " + couponId));

        // Step 2: Fetch the cart by ID
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found with ID: " + cartId));

        // Step 3: Calculate discount based on coupon type
        double discount = 0.0;

        switch (coupon.getType()) {
            case "cart-wise":
                discount = applyCartWiseDiscount(cart, coupon);
                break;

            case "product-wise":
                discount = applyProductWiseDiscount(cart, coupon);
                break;

            case "bxgy":
                discount = applyBxGyDiscount(cart, coupon);
                break;

            default:
                throw new RuntimeException("Unsupported coupon type: " + coupon.getType());
        }

        // Step 4: Update the cart with the applied discount
        cart.setTotalDiscount(cart.getTotalDiscount() + discount);
        cart.setFinalPrice(cart.getTotalPrice() - cart.getTotalDiscount());
        cartRepository.save(cart);

        // Step 5: Create a CouponApplication record
        CouponApplication couponApplication = new CouponApplication();
        couponApplication.setCoupon(coupon);
        couponApplication.setCart(cart);
        couponApplication.setDiscountApplied(discount);

        // Save and return the coupon application
        return couponApplicationRepository.save(couponApplication);
    }

    // Helper method for cart-wise discount
    private double applyCartWiseDiscount(Cart cart, Coupon coupon) {
        String details = coupon.getDetails(); // JSON string
        // Parse JSON (use a library like Jackson or Gson for real cases)
        int threshold = 100; // Example: Extracted from details
        int discountPercent = 10; // Example: Extracted from details

        if (cart.getTotalPrice() > threshold) {
            return cart.getTotalPrice() * discountPercent / 100.0;
        }
        return 0.0;
    }

    // Helper method for product-wise discount
    private double applyProductWiseDiscount(Cart cart, Coupon coupon) {
        String details = coupon.getDetails(); // JSON string
        // Parse JSON (use a library like Jackson or Gson for real cases)
        long productId = 1; // Example: Extracted from details
        int discountPercent = 20; // Example: Extracted from details

        return cart.getItems().stream()
                .filter(item -> item.getProductId() == productId)
                .mapToDouble(item -> item.getPrice() * item.getQuantity() * discountPercent / 100.0)
                .sum();
    }

    // Helper method for BxGy discount
    private double applyBxGyDiscount(Cart cart, Coupon coupon) {
        String details = coupon.getDetails(); // JSON string
        // Parse JSON (use a library like Jackson or Gson for real cases)
        int buyQuantity = 2; // Example: Extracted from details
        int freeQuantity = 1; // Example: Extracted from details
        long buyProductId = 1; // Example: Extracted from details
        long freeProductId = 3; // Example: Extracted from details

        // Calculate how many free items to apply
        int eligibleGroups = (int) cart.getItems().stream()
                .filter(item -> item.getProductId() == buyProductId)
                .mapToInt(item -> item.getQuantity())
                .sum() / buyQuantity;

        double freeProductPrice = cart.getItems().stream()
                .filter(item -> item.getProductId() == freeProductId)
                .mapToDouble(item -> item.getPrice())
                .findFirst()
                .orElse(0.0);

        return eligibleGroups * freeQuantity * freeProductPrice;
    }
}

