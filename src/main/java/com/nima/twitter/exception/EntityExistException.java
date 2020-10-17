package com.nima.twitter.exception;


public class EntityExistException extends RuntimeException{
    public EntityExistException(String message) {
        super(message);
    }

    public EntityExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityExistException(Throwable cause) {
        super(cause);
    }
}
