package com.snayder.aej42.service;

import com.snayder.aej42.model.ApiResult;
import com.snayder.aej42.repository.ApiResultRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TranslatorService {

    @Value("${rapidapi.key}")
    private String apiKey;
    @Value("${rapidapi.translator.host}")
    private String host;
    @Value("${rapidapi.translator.url}")
    private String url;

    private final WebClient webClient;
    private final ApiResultRepository repository;

    public TranslatorService(WebClient webClient, ApiResultRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

    public Mono<ApiResult> translate(String text, String targetLang) {
        Map<String, Object> body = Map.of(
            "q", text,
            "source", "es",
            "target", targetLang
        );

        return webClient.post()
            .uri(url)
            .header("x-rapidapi-key", apiKey)
            .header("x-rapidapi-host", host)
            .header("Content-Type", "application/json")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(Map.class)
            .flatMap(resp -> {
                ApiResult result = new ApiResult(
                    null, "translator", text + " -> " + targetLang, resp.toString(), LocalDateTime.now()
                );
                return repository.save(result);
            });
    }
}