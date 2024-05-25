package com.example.finalproject.exception.role;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message)
    {
        super(message);
    }
}
