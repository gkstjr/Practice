package com.info.platform.domain.coupon;

import com.info.platform.domain.coupon.model.IssuedCoupon;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IssuedCouponService {

    private final IssuedCouponRepository issuedCouponRepository;
    public long applyCoupon(Long issuedCouponId, long originalAmount) {
        if (issuedCouponId == null) {
            return originalAmount; // 쿠폰이 없는 경우 원래 금액 그대로
        }

        IssuedCoupon issuedCoupon = issuedCouponRepository.findByIdWithLock(issuedCouponId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ISSUED_COUPON_NOT_FOUND));

        return issuedCoupon.applyDiscount(originalAmount);
    }
}
