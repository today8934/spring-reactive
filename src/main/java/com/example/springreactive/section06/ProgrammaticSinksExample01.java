package com.example.springreactive.section06;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

@Slf4j
public class ProgrammaticSinksExample01 {

    public static void main(String[] args) {
        int tasks = 6;

        Sinks.Many<String> unicastSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> fluxView = unicastSink.asFlux();
        IntStream
                .range(1, tasks)
                .forEach(n -> new Thread(() -> {
                    unicastSink.emitNext(doTask(n), Sinks.EmitFailureHandler.FAIL_FAST);
                    log.info("# emitted data: task {}", n);
                }).start());

        fluxView
                .publishOn(Schedulers.parallel())
                .map(result -> result + " processed")
                .doOnNext(n -> log.info("# mapped data: {}", n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# consumed data: {}", data));
    }

    private static String doTask(int taskNumber) {
        return "task " + taskNumber + " result";
    }
}
