package com.info.platform.infrastructure.payment;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component @Getter @Setter
@ConfigurationProperties(prefix = "payment")
public class PaymentInfo {
    private String secretKey;
    private String baseUrl;
    private String confirmEndpoint;

    public String getConfirmUrl() {
        return baseUrl + confirmEndpoint;
    }
}
