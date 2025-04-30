package com.info.platform.application.point;

import com.info.platform.infrastructure.payment.dto.PaymentConfirmRequest;
import lombok.Getter;

public record PointChargeCommand(
        String paymentKey,
        String orderId,
        Long couponId,
        long requestedAmount
) {

    public PaymentConfirmRequest toConfirmRequest(long finalPayAmount) {
        return new PaymentConfirmRequest(
                paymentKey,
                orderId,
                finalPayAmount
        );
    }
}
