package com.appschef.intern.minimarket.exception;

import java.util.Map;

public class ValidationException extends RuntimeException {
    private Map<String, String> errors;

    public ValidationException(Map<String, String> errors) {
        super("Input tidak valid");
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
