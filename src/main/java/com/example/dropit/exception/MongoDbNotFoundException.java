package com.example.dropit.exception;

public class MongoDbNotFoundException extends RuntimeException{
    public MongoDbNotFoundException(String message) {
        super(message);
    }
    public MongoDbNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
