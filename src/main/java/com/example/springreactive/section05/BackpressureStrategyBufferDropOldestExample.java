package com.example.springreactive.section05;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@Slf4j
public class BackpressureStrategyBufferDropOldestExample {

    public static void main(String[] args) {
        Flux
                .interval(Duration.ofMillis(1L))
                .doOnNext(data -> log.info("# emitted data: {}", data))
                .onBackpressureBuffer(
                        2,
                        dropped -> log.info("# dropped data: {}", dropped),
                        BufferOverflowStrategy.DROP_OLDEST
                )
                .doOnNext(data -> log.info("# emitted by Buffer data: {}", data))
                .publishOn(Schedulers.parallel(), false, 1)
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
