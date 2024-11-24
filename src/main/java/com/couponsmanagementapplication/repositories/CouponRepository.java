package com.couponsmanagementapplication.repositories;

import com.couponsmanagementapplication.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByType(String type);


}
