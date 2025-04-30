package com.info.platform.controller.point.dto;

import com.info.platform.application.point.PointChargeCommand;

public record PointChargeRequest(
        String paymentKey,
        String orderId,
        Long couponId, //review = 1, 2 로 정가쿠폰 , 할인률쿠폰 임시로
        long requestedAmount
) {
    public PointChargeCommand toCommand() {
        return new PointChargeCommand(
                paymentKey,
                orderId,
                couponId,
                requestedAmount);
    }
}
