package com.info.platform.infrastructure.payment.exception;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class PaymentExceptionInterceptor implements ClientHttpRequestInterceptor {
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        try{
            return execution.execute(request,body);
        }catch (IOException e) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_TIMEOUT,e);
        }catch (Exception e) {
            throw new PaymentException(PaymentErrorCode.PAYMENT_CONFIRM_FAILED,e);
        }
    }
}
