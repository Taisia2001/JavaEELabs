package org.shutiak.lab6.exceptions;

import lombok.Getter;

import java.util.List;

public class ConstraintViolationException extends RuntimeException {

    @Getter
    private final List<String> errors;

    public ConstraintViolationException(final List<String> errors, String message) {
        super("You have errors in you object: "+message);
        this.errors = errors;
    }

}
