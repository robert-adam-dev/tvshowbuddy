package br.com.tvshowbuddy.exception;

public class SeriesNotFoundException extends BusinessException {
    private static final String ERROR_CODE = "404";

    public SeriesNotFoundException(String id) {
        super(String.format("Series not found with id: %s", id), ERROR_CODE);
    }

    public SeriesNotFoundException(String message, String id) {
        super(String.format("%s: %s", message, id), ERROR_CODE);
    }
}