package org.otus.solodov.exception;

public class IllegalAnnotationAttributeException extends RuntimeException {
    public IllegalAnnotationAttributeException(String errorMessage) {
        super(errorMessage);
    }
}