package org.example.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ClientException extends Exception {

    public ClientException(String message) {
        super(message);
    }
}
