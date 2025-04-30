package com.info.platform.domain.point.model;

import com.info.platform.support.BaseEntity;
import com.info.platform.support.exception.BusinessException;
import com.info.platform.support.exception.ErrorCode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter @NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    private long balance;

    public Point(Long memberId) {
        this.memberId = memberId;
        this.balance = 0L;
    }
    public void charge(long chargeAmount) {
        if (chargeAmount <= 0) throw new BusinessException(ErrorCode.INVALID_CHARGE_AMOUNT,"충전 금액은 0 이상이어야 합니다.");
        this.balance += chargeAmount;
    }
}
