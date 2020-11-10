package com.gc.bob.mig.point.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "naver")
@Getter
@Setter
public class NaverProperties {

    private String clientId;
    private String clientSecret;
}
