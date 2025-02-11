package br.com.tvshowbuddy.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SeriesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleSeriesNotFound(SeriesNotFoundException ex) {
        return createErrorResponse(ex.getCode(), List.of(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleJsonParseException(HttpMessageNotReadableException ex) {
        String errorMessage = "Invalid request format. Please check the field values.";

        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            Class<?> targetType = invalidFormatException.getTargetType();
            if (targetType.isEnum()) {
                String validValues = Arrays.stream(targetType.getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
                errorMessage = String.format("Invalid value '%s' for field. Accepted values are: [%s]",
                        invalidFormatException.getValue(), validValues);
            }

            if (targetType == Boolean.class || targetType == boolean.class) {
                String fieldName = invalidFormatException.getPath().getFirst().getFieldName();
                errorMessage = String.format("Invalid value for field '%s'. Accepted values are: true or false",
                        fieldName);
            }
        }

        return createErrorResponse("400", List.of(errorMessage));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> String.format("'%s': %s", error.getField(), error.getDefaultMessage()))
                .toList();

        return createErrorResponse("400", messages);
    }

    private ErrorResponse createErrorResponse(String code, List<String> messages) {
        return ErrorResponse.builder()
                .code(code)
                .messages(messages)
                .timestamp(LocalDateTime.now())
                .build();
    }
}