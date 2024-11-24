package com.couponsmanagementapplication.controllers;


import com.couponsmanagementapplication.dtos.CouponRequestDTO;
import com.couponsmanagementapplication.dtos.CouponResponseDTO;
import com.couponsmanagementapplication.models.Coupon;
import com.couponsmanagementapplication.services.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {

        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponResponseDTO> createCoupon(@RequestBody CouponRequestDTO couponRequestDTO) {
        Coupon coupon=new Coupon();
        coupon.setType(couponRequestDTO.getType());
        coupon.setDetails(couponRequestDTO.getDetails());
        coupon.setExpiryDate(coupon.getExpiryDate());
        // Save Coupon and Convert to ResponseDTO
        Coupon savedCoupon=couponService.createCoupon(coupon);
        CouponResponseDTO couponResponseDTO= convertToResponseDTO(savedCoupon);
        return ResponseEntity.ok(couponResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<CouponResponseDTO>> getAllCoupons() {
        List<Coupon> coupons=couponService.getAllCoupons();
        List<CouponResponseDTO> responseDTOs=coupons.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> getCouponById(@PathVariable Long id) {
        return couponService.getCouponById(id)
                .map(this::convertToResponseDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CouponResponseDTO> updateCoupon(@PathVariable Long id,@RequestBody CouponRequestDTO requestDTO) {
        // Convert RequestDTO to Domain Model
        Coupon coupon = new Coupon();
        coupon.setType(requestDTO.getType());
        coupon.setDetails(requestDTO.getDetails());
        coupon.setExpiryDate(requestDTO.getExpiryDate());

        // Update Coupon and Convert to ResponseDTO
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        CouponResponseDTO responseDTO = convertToResponseDTO(updatedCoupon);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return ResponseEntity.noContent().build();
    }

    private CouponResponseDTO convertToResponseDTO(Coupon coupon) {
        CouponResponseDTO responseDTO = new CouponResponseDTO();
        responseDTO.setId(coupon.getId());
        responseDTO.setType(coupon.getType());
        responseDTO.setDetails(coupon.getDetails());
        responseDTO.setExpiryDate(coupon.getExpiryDate());
        return responseDTO;
    }
}

