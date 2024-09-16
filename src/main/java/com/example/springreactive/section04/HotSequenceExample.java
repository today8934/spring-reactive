package com.example.springreactive.section04;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

@Slf4j
public class HotSequenceExample {

    public static void main(String[] args) throws InterruptedException {
        Flux<String> concertFlux = Flux.fromStream(Stream.of("Singer A", "Singer B", "Singer C", "Singer D", "Singer E"))
                .delayElements(Duration.ofSeconds(1)).share();// share(): 원본 Flux를 여러 Subscriber가 공유한다

        concertFlux.subscribe(singer -> log.info("# Listener1: {}", singer));

        Thread.sleep(2500);

        concertFlux.subscribe(singer -> log.info("# Listener2: {}", singer));

        Thread.sleep(3000);
    }
}
