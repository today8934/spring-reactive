package com.example.springreactive.section04;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.Arrays;

@Slf4j
public class ColdSequenceExample {

    public static void main(String[] args) {
        Flux<String> coldFlux = Flux.fromIterable(Arrays.asList("RED", "YELLOW", "PINK"))
                .map(String::toLowerCase);

        coldFlux.subscribe(color -> log.info("# Subscriber1: {}", color));
        log.info("-------------------------------------------------");
        coldFlux.subscribe(color -> log.info("# Subscriber2: {}", color));
    }
}
