package com.info.platform.infrastructure.coupon;

import com.info.platform.domain.coupon.model.IssuedCoupon;
import com.info.platform.domain.coupon.IssuedCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class IssuedCouponRepositoryImpl implements IssuedCouponRepository {

    private final IssuedCouponJpaRepo issuedCouponJpaRepo;
    @Override
    public Optional<IssuedCoupon> findByIdWithLock(Long id) {
        return issuedCouponJpaRepo.findByIdWithLock(id);
    }

    @Override
    public IssuedCoupon save(IssuedCoupon issuedCoupon) {
        return issuedCouponJpaRepo.save(issuedCoupon);
    }
}
