package com.mralifr.arisan.application.exceptions;

import com.mralifr.arisan.application.models.responses.ApiError;
import com.mralifr.arisan.application.models.responses.ApiSubError;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.hibernate.FetchNotFoundException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(FetchNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ApiError handleFetchNotFoundException(FetchNotFoundException ex, WebRequest request) {
        return ApiError.builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<ApiSubError> subErrors = ex.getConstraintViolations().stream().map(
                constraint -> ApiSubError.builder()
                        .field(((PathImpl) constraint.getPropertyPath()).getLeafNode().asString())
                        .rejectedValue(constraint.getInvalidValue())
                        .message(constraint.getMessage())
                        .build()
        ).collect(Collectors.toList());
        return ApiError.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .subErrors(subErrors)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String message = String.format(
                "Validation failed for object='%s'. Error count: %s",
                ex.getObjectName(),
                ex.getErrorCount()
        );

        List<ApiSubError> subErrors = ex.getFieldErrors().stream().map(
                constraint -> ApiSubError.builder()
                        .field(constraint.getField())
                        .rejectedValue(constraint.getRejectedValue())
                        .message(constraint.getDefaultMessage())
                        .build()
        ).collect(Collectors.toList());

        return ApiError.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .message(message)
                .description(request.getDescription(false))
                .subErrors(subErrors)
                .build();
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ApiError handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
        return ApiError.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .message("JWT Token Expired")
                .description(request.getDescription(false))
                .build();
    }

    @ExceptionHandler(JwtException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ApiError handleJwtException(JwtException ex, WebRequest request) {
        return ApiError.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .timestamp(LocalDateTime.now())
                .message("JWT Token Unrecognized")
                .description(request.getDescription(false))
                .build();
    }
}
