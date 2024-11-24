package com.couponsmanagementapplication.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
public class CouponApplicationRequestDTO {

    private Long couponId;

    private Long cartId;
}
