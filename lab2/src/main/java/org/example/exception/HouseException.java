package org.example.exception;

public class HouseException extends RuntimeException {

    public HouseException() {
    }

    public HouseException(String message) {
        super(message);
    }
}
