package org.otus.solodov.exception;

public class TestExecutionException extends RuntimeException {
    public TestExecutionException(String errorMessage) {
        super(errorMessage);
    }
}
