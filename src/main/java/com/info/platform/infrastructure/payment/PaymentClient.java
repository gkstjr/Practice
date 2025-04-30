package com.info.platform.infrastructure.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.info.platform.infrastructure.payment.dto.PaymentConfirmRequest;
import com.info.platform.infrastructure.payment.dto.PaymentConfirmResponse;
import com.info.platform.infrastructure.payment.dto.PaymentFailResponse;
import com.info.platform.infrastructure.payment.exception.PaymentErrorCode;
import com.info.platform.infrastructure.payment.exception.PaymentException;
import com.info.platform.infrastructure.payment.exception.PaymentExceptionInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Base64;

@Component
@Slf4j
public class PaymentClient {
    private static final String BASIC_DELIMITER = ":";
    private static final String AUTH_HEADER_PREFIX = "Basic ";
    //review - connect-time-out 은 결제는 사용자가 어느정도 용인가능하므로 1초 , read-out 은 토스서버에 권장 시간 설정
    private static final int CONNECT_TIMEOUT_SECONDS = 1;
    private static final int READ_TIMEOUT_SECONDS = 30;

    private final ObjectMapper objectMapper;
    private final PaymentInfo paymentInfo;
    private RestClient restClient;

    public PaymentClient(PaymentInfo paymentInfo , ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.paymentInfo = paymentInfo;
        this.restClient = RestClient.builder()
                .requestFactory(createPaymentRequestFactory())
                .requestInterceptor(new PaymentExceptionInterceptor())
                .defaultHeader(HttpHeaders.AUTHORIZATION,createPaymentAuthHeader(paymentInfo))
                .build();
    }
    public PaymentConfirmResponse confirmPayment(PaymentConfirmRequest paymentConfirmRequest) {
        return restClient.post()
                .uri(paymentInfo.getConfirmUrl())
                .contentType(MediaType.APPLICATION_JSON)
                .body(paymentConfirmRequest)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (request,response) -> {
                    throw new PaymentException(getPaymentErrorCode(response));
                })
                .body(PaymentConfirmResponse.class);
    }

    //review - 토스 결제 승인 API 에러 코드 문서 참고
    private PaymentErrorCode getPaymentErrorCode(final ClientHttpResponse response) throws IOException {
        PaymentFailResponse failResponse = objectMapper.readValue(
                response.getBody(), PaymentFailResponse.class);
        return PaymentErrorCode.findByName(failResponse.code());
    }

    private ClientHttpRequestFactory createPaymentRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(CONNECT_TIMEOUT_SECONDS).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(READ_TIMEOUT_SECONDS).toMillis());
        return factory;
    }
    private String createPaymentAuthHeader(PaymentInfo paymentInfo) {
        byte[] encodedBytes = Base64.getEncoder().encode((paymentInfo.getSecretKey() + BASIC_DELIMITER).getBytes(StandardCharsets.UTF_8));
        return AUTH_HEADER_PREFIX + new String(encodedBytes);
    }
}
