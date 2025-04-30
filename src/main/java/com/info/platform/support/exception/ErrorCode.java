package com.info.platform.support.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
public enum ErrorCode implements ErrorType {
    //POFILE
    PROFILE_INVALID_SORT_FIELD(HttpStatus.BAD_REQUEST, "지원하지 않는 정렬 필드입니다."),
    PROFILE_NOT_FOUND(HttpStatus.BAD_REQUEST,"존재하지 않는 profile 입니다." ),
    //coupon
    COUPON_ALREADY_USED(HttpStatus.BAD_REQUEST,"이미 사용한 쿠폰입니다." ),
    COUPON_EXPIRED(HttpStatus.BAD_REQUEST,"유효기간이 지난 쿠폰입니다." ),
    ISSUED_COUPON_NOT_FOUND(HttpStatus.BAD_REQUEST, "발급 받은 쿠폰이 없습니다." ),
    INVALID_COUPON_DISCOUNT_TYPE(HttpStatus.BAD_REQUEST,"유효하지 않은 할인 타입입니다." ),
    //예상못한 예외
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류" ),
    //포인트
    POINT_NOT_FOUND(HttpStatus.BAD_REQUEST,"포인트가 존재하지 않습니다." ),
    INVALID_CHARGE_AMOUNT(HttpStatus.BAD_REQUEST,"충전 금액을 다시 입력해주세요." ),
    ;

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status , String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
