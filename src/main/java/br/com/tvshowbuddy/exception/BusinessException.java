package br.com.tvshowbuddy.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

    private final String code;

    protected BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }
}