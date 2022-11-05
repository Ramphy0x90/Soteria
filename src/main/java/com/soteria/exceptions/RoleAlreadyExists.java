package com.soteria.exceptions;

public class RoleAlreadyExists extends RuntimeException{
    public RoleAlreadyExists(String message) {
        super(message);
    }
}
