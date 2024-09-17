package org.example.exception;

public class IncompatibleAnnotationException extends RuntimeException {
    public IncompatibleAnnotationException(String errorMessage) {
        super(errorMessage);
    }
}
