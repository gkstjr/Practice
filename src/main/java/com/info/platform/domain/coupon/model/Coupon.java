package com.info.platform.domain.coupon.model;

import com.info.platform.domain.coupon.DiscountType;
import com.info.platform.support.BaseEntity;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private DiscountType discountType;

    private long discountValue; // 퍼센트면 %값, 정액이면 원화
    private LocalDateTime expiredAt;

    public Coupon(String name , String discountType, long discountValue , LocalDateTime expiredAt) {
        this.name = name;
        this.discountType = parseDiscountType(discountType);
        this.discountValue = discountValue;
        this.expiredAt = expiredAt;
    }

    public IssuedCoupon issueTo(Long memberId) {
        // review 유효성 검사, 중복 발급 검사 등은 생략
        return new IssuedCoupon(this, memberId);
    }

    public long calculateDiscountedAmount(int originalAmount) {
        return discountType.calculateDiscountedAmount(originalAmount, discountValue);
    }


    private DiscountType parseDiscountType(String discountType) {
        try {
            return DiscountType.valueOf(discountType.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new BusinessException(ErrorCode.INVALID_COUPON_DISCOUNT_TYPE, "유효하지 않은 할인 타입입니다: " + discountType);
        }
    }
}
