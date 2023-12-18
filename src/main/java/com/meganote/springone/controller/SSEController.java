package com.meganote.springone.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@Slf4j
public class SSEController {

    @PostMapping(value = "/sse", produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
    public SseEmitter sse() {

        log.info("Step in sse()");

        SseEmitter emitter = new SseEmitter(0L);
        ExecutorService executor = Executors.newCachedThreadPool();

        executor.execute(() -> {

            for (int i = 0; i < 15; i++) {

                SseEmitter.SseEventBuilder event = SseEmitter.event()
                        .data("SSE - localtime: " + LocalTime.now())
                        .id(String.valueOf(i))
                        .name("delta");

                try {
                    emitter.send(event);
                    Thread.sleep(1000);
                } catch (Exception exception) {
                    log.error(exception.getMessage());
                    emitter.completeWithError(exception);
                }
            }

            emitter.complete();
        });

        return emitter;
    }

}
