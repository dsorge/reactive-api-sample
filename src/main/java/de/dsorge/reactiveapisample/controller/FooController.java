package de.dsorge.reactiveapisample.controller;

import de.dsorge.reactiveapisample.model.Foo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import java.time.Duration;

@RestController
@RequestMapping("/foos")
public class FooController {

    @GetMapping
    public Flux<Foo> getFoos() {
        // Create a Flux that emits a new Foo resource every second
        return Flux.interval(Duration.ofSeconds(1))
                .map(i -> new Foo(i, "Foo " + i));
    }
}

