package com.snayder.aej42.service;

import com.snayder.aej42.model.ApiResult;
import com.snayder.aej42.repository.ApiResultRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ChatGPTService {

    @Value("${rapidapi.key}")
    private String apiKey;
    @Value("${rapidapi.chatgpt.host}")
    private String host;
    @Value("${rapidapi.chatgpt.url}")
    private String url;

    private final WebClient webClient;
    private final ApiResultRepository repository;

    public ChatGPTService(WebClient webClient, ApiResultRepository repository) {
        this.webClient = webClient;
        this.repository = repository;
    }

        public Mono<ApiResult> chat(String message) {
        Map<String, Object> body = Map.of(
            "messages", List.of(Map.of("role", "user", "content", message)),
            "web_access", false
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
                    null, "chatgpt", message, resp.toString(), LocalDateTime.now()
                );
                return repository.save(result);
            });
    }
}