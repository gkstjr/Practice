package com.info.platform.infrastructure.coupon;

import com.info.platform.domain.coupon.model.Coupon;
import com.info.platform.domain.coupon.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl  implements CouponRepository {

    private final CouponJpaRepo couponJpaRepo;

    @Override
    public void save(Coupon coupon) {
        couponJpaRepo.save(coupon);
    }
}
