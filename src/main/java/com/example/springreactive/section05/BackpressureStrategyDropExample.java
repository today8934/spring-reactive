package com.example.springreactive.section05;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BackpressureStrategyDropExample {

    public static void main(String[] args) {
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureDrop(dropped -> log.info("# dropped data: {}", dropped))
                .publishOn(Schedulers.parallel())
                .subscribe(
                        data -> {
                            try {
                                Thread.sleep(5000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            log.info("# consumed data: {}", data);
                        },
                        error -> log.error("# error: {}", error)
                );
    }
}
