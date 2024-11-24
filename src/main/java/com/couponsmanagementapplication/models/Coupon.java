package com.couponsmanagementapplication.models;




import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Data
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type; // cart-wise, product-wise, bxgy
    private String details; // JSON string for flexibility in storing conditions
    private  String expiryDate;
}

