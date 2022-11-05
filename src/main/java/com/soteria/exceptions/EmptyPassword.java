package com.soteria.exceptions;

public class EmptyPassword extends RuntimeException{
    public EmptyPassword(String message) {
        super(message);
    }
}
