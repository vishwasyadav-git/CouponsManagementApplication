package com.couponsmanagementapplication.dtos;

import lombok.Data;

// ResponseDTO for returning coupon information
@Data
public class CouponResponseDTO {
    private Long id;
    private String type;
    private String details;
    private String expiryDate;
    private String description;
}
