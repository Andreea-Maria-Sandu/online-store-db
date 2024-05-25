package com.example.finalproject.exception.user;

public class UserAlreadyTakenException extends RuntimeException{
    public UserAlreadyTakenException(String message)
    {
        super(message);
    }
}
