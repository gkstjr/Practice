package com.info.platform.infrastructure.payment.dto;


public record PaymentConfirmResponse(
        String paymentKey,
        String orderId,
        long totalAmount,
        String requestedAt,
        String approvedAt,
        String status
) {
}
