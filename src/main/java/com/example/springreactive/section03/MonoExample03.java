package com.example.springreactive.section03;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;

@Slf4j
public class MonoExample03 {

    public static void main(String[] args) {
        URI worldTimeUri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("worldtimeapi.org")
                .port(443)
                .path("api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Mono.just(
                restTemplate.exchange(worldTimeUri, HttpMethod.GET, new HttpEntity<>(httpHeaders), String.class)
        )
                .map(response -> {
                    DocumentContext jsonContext = JsonPath.parse(response.getBody());
                    return jsonContext.<String>read("$.datetime");
                })
                .subscribe(
                        data -> log.info("# emitted data: {}", data),
                        error -> log.error(error.getMessage(), error),
                        () -> log.info("# emitted onComplete Signal")
                );
    }
}
