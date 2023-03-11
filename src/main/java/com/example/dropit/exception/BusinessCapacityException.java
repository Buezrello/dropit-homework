package com.example.dropit.exception;

public class BusinessCapacityException extends RuntimeException{
    public BusinessCapacityException(String message) {
        super(message);
    }
    public BusinessCapacityException(String message, Throwable cause) {
        super(message, cause);
    }
}
