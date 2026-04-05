package com.snayder.aej42.repository;

import com.snayder.aej42.model.ApiResult;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ApiResultRepository extends ReactiveMongoRepository<ApiResult, String> {
    Flux<ApiResult> findByApiType(String apiType);
}