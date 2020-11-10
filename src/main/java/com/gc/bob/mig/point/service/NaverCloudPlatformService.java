package com.gc.bob.mig.point.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.gc.bob.mig.point.model.NaverGeocodeResponse;
import com.gc.bob.mig.point.model.NaverReverseGeocodeResponse;
import com.gc.bob.mig.point.property.NaverProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NaverCloudPlatformService {

    private final NaverProperties naverProperties;

    private static final String GEOCODE_URL = "https://naveropenapi.apigw.ntruss.com";

    private WebClient webClient;

    @PostConstruct
    private void init() {
        webClient = WebClient
            .builder()
            .baseUrl(GEOCODE_URL)
            .defaultHeader("X-NCP-APIGW-API-KEY-ID", naverProperties.getClientId())
            .defaultHeader("X-NCP-APIGW-API-KEY", naverProperties.getClientSecret())
            .build();
    }

    public NaverGeocodeResponse geocode(String query) {
        NaverGeocodeResponse response = webClient.get()
            .uri("/map-geocode/v2/geocode?query={query}", query)
            .exchange()
            .flatMap(it -> it.bodyToMono(NaverGeocodeResponse.class))
            .flux()
            .toStream()
            .findAny()
            .orElse(new NaverGeocodeResponse());
        return response;
    }

    public NaverReverseGeocodeResponse reverseGeocode(Double lon, Double lat) {
        String coords = lon + "," + lat;
        NaverReverseGeocodeResponse reverseGeocodeResponse = webClient.get()
            .uri("/map-reversegeocode/v2/gc?output=json&orders=admcode&coords={coords}", coords)
            .exchange()
            .flatMap(it -> it.bodyToMono(NaverReverseGeocodeResponse.class))
            .flux()
            .toStream()
            .findAny()
            .orElse(new NaverReverseGeocodeResponse());
        return reverseGeocodeResponse;
    }

}
