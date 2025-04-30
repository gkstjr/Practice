package com.info.platform.controller.point.dto;

import com.info.platform.application.point.PointChargeResult;

public record PointChargeResponse(
        String paymentKey,
        String orderId,
        long payAmount, //실제 결제금액
        long discountedAmount, //할인된 포인트금액
        long finalAmount, //실제 적립된 포인트금액
        long currentBalance //충전 후 포인트 잔액
) {
    public static PointChargeResponse from(PointChargeResult result) {
        return new PointChargeResponse(
                result.paymentKey(),
                result.orderId(),
                result.payAmount(),
                result.discountedAmount(),
                result.finalAmount(),
                result.currentBalance()
        );
    }
}
