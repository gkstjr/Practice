package com.info.platform.infrastructure.coupon;

import com.info.platform.domain.coupon.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepo extends JpaRepository<Coupon,Long> {
}
