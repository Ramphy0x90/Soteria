package com.soteria.exceptions.credential;

public class CredentialAlreadyExists extends RuntimeException {
    public CredentialAlreadyExists(String message) {
        super(message);
    }
}
