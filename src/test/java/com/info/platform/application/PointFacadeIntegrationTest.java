package com.info.platform.application;

import com.info.platform.application.point.PointChargeCommand;
import com.info.platform.application.point.PointChargeResult;
import com.info.platform.application.point.PointFacade;
import com.info.platform.domain.coupon.*;
import com.info.platform.domain.coupon.model.Coupon;
import com.info.platform.domain.coupon.model.IssuedCoupon;
import com.info.platform.domain.point.model.Point;
import com.info.platform.domain.point.PointRepository;
import com.info.platform.infrastructure.payment.PaymentClient;
import com.info.platform.infrastructure.payment.dto.PaymentConfirmResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@Transactional
public class PointFacadeIntegrationTest {


    @Autowired
    private PointFacade pointFacade;

    @Autowired
    private IssuedCouponRepository issuedCouponRepository;

    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private CouponRepository couponRepository;
    @MockitoBean
    private PaymentClient paymentClient;

    @Test
    void 포인트충전_성공() {
        // given
        Long memberId = 1L;
        long requestedAmount = 5000L;
        Point point = new Point(memberId); //처음 0포인트
        pointRepository.save(point);
        Coupon coupon = new Coupon("정액 할인 쿠폰", DiscountType.FIXED.name(), 1000 , LocalDateTime.now().plusDays(3));
        couponRepository.save(coupon);
        IssuedCoupon issued = new IssuedCoupon(coupon, memberId);
        issuedCouponRepository.save(issued);
        // 외부 API 응답 mock
        given(paymentClient.confirmPayment(any())).willReturn(
                new PaymentConfirmResponse(
                        "payment-key",
                        "order-id",
                        requestedAmount - coupon.getDiscountValue(),
                        "2024-04-30T10:00:00",
                        "2024-04-30T10:00:05",
                        "DONE"
                )
        );

        PointChargeCommand command = new PointChargeCommand(
                "payment-key",
                "order-id",
                issued.getId(),
                requestedAmount
        );

        // when
        PointChargeResult result = pointFacade.chargePoint(command, memberId);

        // then
        assertThat(result.paymentKey()).isEqualTo("payment-key");
        assertThat(result.orderId()).isEqualTo("order-id");
        assertThat(result.payAmount()).isEqualTo(4000L); // 5000 - 1000
        assertThat(result.discountedAmount()).isEqualTo(1000L);
        assertThat(result.finalAmount()).isEqualTo(5000L);
        assertThat(result.currentBalance()).isEqualTo(5000L);
    }
}
