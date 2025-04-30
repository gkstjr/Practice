package com.info.platform.support.exception;

import org.springframework.http.HttpStatus;

public interface ErrorType {
    HttpStatus getStatus();
    String getMessage();
}
