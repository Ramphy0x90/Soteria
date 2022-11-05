package com.soteria.exceptions;

public class RoleNotFound extends RuntimeException{
    public RoleNotFound(String message) {
        super(message);
    }
}
