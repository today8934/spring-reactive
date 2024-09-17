package com.example.springreactive.section07;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class ParallelExample03 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.fromArray(new Integer[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19})
                .parallel(4)
                .runOn(Schedulers.parallel())
                .doOnTerminate(latch::countDown)
                .subscribe(data -> log.info("parallel: {}", data));

        latch.await();
        latch.countDown();
    }
}
