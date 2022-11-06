package com.soteria.exceptions.user;

public class EmptyPassword extends RuntimeException{
    public EmptyPassword(String message) {
        super(message);
    }
}
