package com.example.springreactive.section07;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class SchedulerOperatorExample04 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.fromArray(new Integer[] {1, 3, 5, 7})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("fromArray: {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("filter: {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("map: {}", data))
                .doOnTerminate(latch::countDown)
                .subscribe(data -> log.info("subscribe: {}", data));

        latch.await();
        latch.countDown();
    }
}
