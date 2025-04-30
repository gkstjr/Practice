package com.info.platform.application.point;

public record PointChargeResult(
        String paymentKey,
        String orderId,
        long payAmount,
        long discountedAmount,
        long finalAmount,
        long currentBalance
) {
}
