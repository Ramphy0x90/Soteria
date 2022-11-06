package com.soteria.exceptions.role;

public class RoleNotFound extends RuntimeException{
    public RoleNotFound(String message) {
        super(message);
    }
}
