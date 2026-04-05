package com.snayder.aej42.controller;

import com.snayder.aej42.model.ApiResult;
import com.snayder.aej42.repository.ApiResultRepository;
import com.snayder.aej42.service.TranslatorService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.Map;

@RestController
@RequestMapping("/api/translate")
public class TranslatorController {

    private final TranslatorService service;
    private final ApiResultRepository repository;

    public TranslatorController(TranslatorService service, ApiResultRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @PostMapping
    public Mono<ApiResult> translate(@RequestBody Map<String, String> body) {
        return service.translate(body.get("text"), body.get("target"));
    }

    @GetMapping
    public Flux<ApiResult> getAll() {
        return repository.findByApiType("translator");
    }
}