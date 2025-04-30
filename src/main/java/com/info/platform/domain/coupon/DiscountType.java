package com.info.platform.domain.coupon;

public enum DiscountType {
    FIXED { //review - 고정금액할인
        @Override
        public long calculateDiscountedAmount(long originalAmount, long discount) {
            return Math.max(originalAmount - discount, 0); // 할인금액이 결제액 초과 못 하게
        }
    },
    PERCENTAGE { // 비율 할인
        @Override
        public long calculateDiscountedAmount(long originalAmount, long discount) {
            long calculated = Math.min(originalAmount * discount / 100, LIMIT_DISCOUNT); // 최대 할인 한도 적용
            return originalAmount - calculated;
        }
    };
    public abstract long calculateDiscountedAmount(long originalAmount, long discount);
    private static final long LIMIT_DISCOUNT = 5000;
}
