package org.example.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AccountException extends Exception {

    public AccountException(String message) {
        super(message);
    }
}
