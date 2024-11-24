package com.couponsmanagementapplication.repositories;

import com.couponsmanagementapplication.models.CouponApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponApplicationRepository extends JpaRepository<CouponApplication, Long> {
}
