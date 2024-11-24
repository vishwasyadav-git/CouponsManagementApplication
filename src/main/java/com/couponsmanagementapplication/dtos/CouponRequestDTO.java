package com.couponsmanagementapplication.dtos;

import lombok.Data;

@Data
public class CouponRequestDTO {
    private String type;
    private String details;
    private String expiryDate;
}
