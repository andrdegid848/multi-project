package org.example.exception;

public class StreetException extends RuntimeException {

    public StreetException() {
    }

    public StreetException(String message) {
        super(message);
    }
}
