package com.example.springreactive.section06;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Slf4j
public class SinkManyExample05 {

    public static void main(String[] args) {
        Sinks.Many<Integer> replaySink = Sinks.many().replay().all();
        Flux<Integer> fluxView = replaySink.asFlux();

        replaySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("Subscriber1: {}", data));
        fluxView.subscribe(data -> log.info("Subscriber2: {}", data));

        replaySink.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
