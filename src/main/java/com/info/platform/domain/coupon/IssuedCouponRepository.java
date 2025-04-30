package com.info.platform.domain.coupon;

import com.info.platform.domain.coupon.model.IssuedCoupon;

import java.util.Optional;

public interface IssuedCouponRepository {
    Optional<IssuedCoupon> findByIdWithLock(Long id);

    IssuedCoupon save(IssuedCoupon issuedCoupon);
}
