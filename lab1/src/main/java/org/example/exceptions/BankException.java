package org.example.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BankException extends Exception {

    public BankException(String message) {
        super(message);
    }
}
