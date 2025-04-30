package com.info.platform.service.coupon;

import com.info.platform.domain.coupon.IssuedCouponRepository;
import com.info.platform.domain.coupon.IssuedCouponService;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class IssuedCouponServiceUnitTest {

    @InjectMocks
    private IssuedCouponService issuedCouponService;

    @Mock
    private IssuedCouponRepository issuedCouponRepository;


    @Test
    void 쿠폰이_존재하지_않으면_예외() {
        // given
        given(issuedCouponRepository.findByIdWithLock(anyLong()))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> issuedCouponService.applyCoupon(1L, 10000L))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ISSUED_COUPON_NOT_FOUND);
    }
}
