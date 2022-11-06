package com.soteria.exceptions.credential;

public class CredentialNotFound extends RuntimeException {
    public CredentialNotFound(String message) {
        super(message);
    }
}
