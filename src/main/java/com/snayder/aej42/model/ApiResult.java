package com.snayder.aej42.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "api_results")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResult {
    @Id
    private String id;
    private String apiType;
    private String request;
    private String response;
    private LocalDateTime timestamp;
}