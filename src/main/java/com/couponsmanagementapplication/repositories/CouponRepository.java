package com.couponsmanagementapplication.repositories;

import com.couponsmanagementapplication.models.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
