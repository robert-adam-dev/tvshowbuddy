package br.com.tvshowbuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeriesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleSeriesNotFound(SeriesNotFoundException ex) {
        return ErrorResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}