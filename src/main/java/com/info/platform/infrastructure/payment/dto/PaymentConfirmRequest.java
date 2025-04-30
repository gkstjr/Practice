package com.info.platform.infrastructure.payment.dto;

public record PaymentConfirmRequest(
        String paymentKey,
        String orderId,
        long amount
) {}
