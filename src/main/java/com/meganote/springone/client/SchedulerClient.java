package com.meganote.springone.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.meganote.springone.model.dto.CompletionsDTO;

@Component
@FeignClient(name = "llm-scheduler-service")
public interface SchedulerClient {

    @PostMapping(value = "/scheduler/completions", produces = { MediaType.TEXT_EVENT_STREAM_VALUE })
    SseEmitter completions(@RequestBody CompletionsDTO CompletionsDTO);
}
