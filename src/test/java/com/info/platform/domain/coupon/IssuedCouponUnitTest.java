package com.info.platform.domain.coupon;

import com.info.platform.domain.coupon.model.Coupon;
import com.info.platform.domain.coupon.model.IssuedCoupon;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class IssuedCouponUnitTest {
    @Test
    void 이미_사용된_쿠폰이면_예외가_발생한다() {
        // given
        Long memberId = 1L;
        Coupon coupon = new Coupon("정액 할인 쿠폰", DiscountType.FIXED.name(), 1000 , LocalDateTime.now().plusDays(5));
        IssuedCoupon issued = coupon.issueTo(memberId);
        issued.applyDiscount(5000);

        // when & then
        assertThatThrownBy(() -> issued.applyDiscount(5000L))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COUPON_ALREADY_USED);
    }

    @Test
    void 만료된_쿠폰이면_예외가_발생한다() {
        // given
        Long memberId = 1L;
        Coupon coupon = new Coupon("정액 할인 쿠폰", DiscountType.FIXED.name(), 1000 , LocalDateTime.now().minusDays(5));
        IssuedCoupon issued = coupon.issueTo(memberId);

        // when & then
        assertThatThrownBy(() -> issued.applyDiscount(5000L))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COUPON_EXPIRED);
    }

    @Test
    void 정상적인_쿠폰이면_할인적용되고_used_상태로_변경된다() {
        // given
        Long memberId = 1L;
        Coupon coupon = new Coupon("정액 할인 쿠폰", DiscountType.FIXED.name(), 1000 , LocalDateTime.now().plusDays(5));
        IssuedCoupon issued = coupon.issueTo(memberId);

        // when
        long result = issued.applyDiscount(5000L);

        // then
        assertThat(result).isEqualTo(4000L);
        assertThat(issued.isUsed()).isTrue();
    }
}
