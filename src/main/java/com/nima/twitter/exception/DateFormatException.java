package com.nima.twitter.exception;


public class DateFormatException extends RuntimeException{
    public DateFormatException(String message) {
        super(message);
    }

    public DateFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateFormatException(Throwable cause) {
        super(cause);
    }
}
