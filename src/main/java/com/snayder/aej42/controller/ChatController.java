package com.snayder.aej42.controller;

import com.snayder.aej42.model.ApiResult;
import com.snayder.aej42.repository.ApiResultRepository;
import com.snayder.aej42.service.ChatGPTService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatGPTService service;
    private final ApiResultRepository repository;

    public ChatController(ChatGPTService service, ApiResultRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public Mono<ApiResult> chat(@RequestBody Map<String, String> body) {
        return service.chat(body.get("message"));
    }

    @GetMapping
    public Flux<ApiResult> getAll() {
        return repository.findByApiType("chatgpt");
    }
}