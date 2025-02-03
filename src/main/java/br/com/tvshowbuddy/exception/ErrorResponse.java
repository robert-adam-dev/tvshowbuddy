package br.com.tvshowbuddy.exception;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ErrorResponse {
    private final String code;
    private final List<String> messages;
    private final LocalDateTime timestamp;
}