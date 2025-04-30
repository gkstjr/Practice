package com.info.platform.application.point;

import com.info.platform.domain.coupon.IssuedCouponService;
import com.info.platform.domain.point.PointService;
import com.info.platform.infrastructure.payment.PaymentClient;
import com.info.platform.infrastructure.payment.dto.PaymentConfirmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointFacade {
    private final IssuedCouponService issuedCouponService;
    private final PointService pointService;
    private final PaymentClient paymentClient;

    @Transactional
    public PointChargeResult  chargePoint(PointChargeCommand command , Long memberId) {
        long finalPayAmount	 = issuedCouponService.applyCoupon(command.couponId(), command.requestedAmount());
        long currentBalance = pointService.chargePoint(memberId,command.requestedAmount());

        PaymentConfirmResponse response = paymentClient.confirmPayment(command.toConfirmRequest(finalPayAmount));

        return new PointChargeResult(
                response.paymentKey(),
                response.orderId(),
                finalPayAmount	, //결제금액
                command.requestedAmount() - finalPayAmount	, // 할인된 금액
                command.requestedAmount(), // 실제 적립된 포인트 금액
                currentBalance // 충전후 포인트 총합
        );
    }
}
