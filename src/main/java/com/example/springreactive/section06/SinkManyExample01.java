package com.example.springreactive.section06;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class SinkManyExample01 {

    public static void main(String[] args) {
        Sinks.Many<Integer> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<Integer> fluxView = unicastSink.asFlux();

        unicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        unicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("Subscriber1: {}", data));

        unicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        //fluxView.subscribe(data -> log.info("Subscriber2: {}", data));
    }
}
