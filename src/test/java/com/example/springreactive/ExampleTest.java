package com.example.springreactive;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

public class ExampleTest {

    @Test
    public void helloReactorTest() {
        Flux<String> sequence = Flux.just("Hello", "Reactor");
        sequence.map(data -> data.toLowerCase())
                .subscribe(data -> System.out.println(data));
    }
}
