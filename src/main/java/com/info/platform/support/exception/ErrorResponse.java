package com.info.platform.support.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;

    public ErrorResponse(HttpStatus status , String message) {
        this.status = status;
        this.message = message;
    }

    public <T extends ErrorType> ErrorResponse(T errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
