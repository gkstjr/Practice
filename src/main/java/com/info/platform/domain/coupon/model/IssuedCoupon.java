package com.info.platform.domain.coupon.model;

import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IssuedCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Coupon coupon;
    private Long memberId;
    private boolean used;

    public IssuedCoupon(Coupon coupon, Long memberId) {
        this.coupon = coupon;
        this.memberId = memberId;
        this.used = false;
    }

    // 할인 적용된 금액 반환
    public long applyDiscount(long originalAmount) {
        validateUsable();
        this.used = true;
        return coupon.getDiscountType()
                .calculateDiscountedAmount(originalAmount, coupon.getDiscountValue());
    }

    private void validateUsable() {
        if (used) throw new BusinessException(ErrorCode.COUPON_ALREADY_USED);
        if (coupon.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException(ErrorCode.COUPON_EXPIRED);
        }
    }
}
