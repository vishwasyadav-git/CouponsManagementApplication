package com.couponsmanagementapplication.controllers;


import com.couponsmanagementapplication.dtos.CouponApplicationRequestDTO;
import com.couponsmanagementapplication.models.Cart;
import com.couponsmanagementapplication.models.Coupon;
import com.couponsmanagementapplication.models.CouponApplication;
import com.couponsmanagementapplication.services.CouponApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon-applications")
public class CouponApplicationController {

    private final CouponApplicationService couponApplicationService;

    public CouponApplicationController(CouponApplicationService couponApplicationService) {
        this.couponApplicationService = couponApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<CouponApplication> applyCoupon(@RequestBody CouponApplicationRequestDTO requestDTO) {
        // Extract IDs from DTO
        Long couponId = requestDTO.getCouponId();
        Long cartId = requestDTO.getCartId();
        // Check if IDs are null before proceeding
        if (couponId == null || cartId == null) {
            throw new IllegalArgumentException("Coupon ID and Cart ID must not be null");
        }
        // Call service to apply the coupon
        CouponApplication appliedCoupon = couponApplicationService.applyCoupon(couponId, cartId);

        // Return the response
        return ResponseEntity.ok(appliedCoupon);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CouponApplication>> getApplicationsForCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(couponApplicationService.getApplicationsForCart(cartId));
    }
}

