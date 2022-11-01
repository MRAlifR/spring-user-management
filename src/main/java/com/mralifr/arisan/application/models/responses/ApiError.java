package com.mralifr.arisan.application.models.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiError {
    int statusCode;
    LocalDateTime timestamp;
    String message;
    String description;
    List<ApiSubError> subErrors;
}
