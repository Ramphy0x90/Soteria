package com.soteria.exceptions.role;

public class RoleAlreadyExists extends RuntimeException{
    public RoleAlreadyExists(String message) {
        super(message);
    }
}
