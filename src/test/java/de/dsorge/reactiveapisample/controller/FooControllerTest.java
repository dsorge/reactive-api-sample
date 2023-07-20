package de.dsorge.reactiveapisample.controller;

import de.dsorge.reactiveapisample.model.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
@AutoConfigureWebTestClient
public class FooControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void testGetFoos() {
        final Flux<Foo> fooFlux = getFoos();

        // Use StepVerifier to verify the emitted Foos
        StepVerifier.create(fooFlux)
                .expectNextCount(5) // Expect 5 Foo objects to be emitted (change the count as needed)
                .thenCancel()
                .verify(Duration.ofSeconds(6)); // Allow a bit of extra time for processing the Flux
    }

    private Flux<Foo> getFoos(){
        // Perform a GET request to /foos endpoint and retrieve the response body as a Flux of Foo
        return webTestClient.get()
                .uri("/foos")
                .exchange()
                .expectStatus().isOk()
                .returnResult(Foo.class)
                .getResponseBody();
    }

    @Test
    public void testGetFoosAndVerify(){
        final Flux<Foo> fooFlux = getFoos();

        // Use StepVerifier to verify the emitted Foos and check foo attributes
        StepVerifier.create(fooFlux)
                .expectNextMatches(foo -> foo.getId() == 0 && foo.getName().equals("Foo 0"))
                .thenCancel()
                .verify(Duration.ofSeconds(6));
    }
}

