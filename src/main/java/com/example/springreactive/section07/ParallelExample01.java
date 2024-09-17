package com.example.springreactive.section07;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class ParallelExample01 {

    public static void main(String[] args) {
        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15})
                .parallel()
                .subscribe(data -> log.info("parallel: {}", data));
    }
}
