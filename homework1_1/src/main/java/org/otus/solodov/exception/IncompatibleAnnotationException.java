package org.otus.solodov.exception;

public class IncompatibleAnnotationException extends RuntimeException {
    public IncompatibleAnnotationException(String errorMessage) {
        super(errorMessage);
    }
}
