package com.example.springreactive.section05;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

@Slf4j
public class BackpressureExample02 {

    public static int count = 0;

    public static void main(String[] args) {
        Flux.range(1, 5)
                .doOnNext(num -> log.info("# emitted number: {}", num))
                .doOnRequest(request -> log.info("# request: {}", request))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(2);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        count++;
                        log.info("# value: {}", value);
                        if (count == 2) {
                            try {
                                Thread.sleep(2000L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            request(2);
                            count = 0;
                        }
                    }
                });
    }
}
