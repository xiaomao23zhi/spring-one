package com.meganote.springone.service;

import java.io.IOException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Component
@Slf4j
public class SSEService {

    private final WebClient.Builder loadBalancedWebClientBuilder;

    public SSEService(WebClient.Builder loadBalancedWebClientBuilder) {
        this.loadBalancedWebClientBuilder = loadBalancedWebClientBuilder;
    }

    public SseEmitter forward(SseEmitter emitter) {

        String baseUrl = "http://spring-one";
        String uri = "/sse";

        ParameterizedTypeReference<ServerSentEvent<String>> type = new ParameterizedTypeReference<ServerSentEvent<String>>() {
        };

        Flux<ServerSentEvent<String>> eventStream = loadBalancedWebClientBuilder.build()
                .post()
                .uri(baseUrl + uri)
                .body(null)
                .retrieve()
                .bodyToFlux(type);
        // Flux<ServerSentEvent<String>> eventStream =
        // loadBalancedWebClientBuilder.build()
        // .post()
        // .uri(baseUrl + uri)
        // .retrieve()
        // .bodyToFlux(type);

        eventStream.subscribe(
                content -> {
                    try {
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(content.data() != null ? content.data() : "{}")
                                .name("DELTA");
                        emitter.send(event);
                    } catch (IOException exception) {
                        emitter.completeWithError(exception);
                        log.error("SSE send ERROR: ", exception);
                    }
                },
                error -> {
                    emitter.completeWithError(error);
                    log.error("SSE subscribe ERROR: ", error);
                },
                () -> {
                    emitter.complete();
                    log.info("SSE subscribe COMPLETED!");
                });

        return emitter;
    }

}
