package com.nima.twitter.exception;


public class Exception404 extends RuntimeException{
    public Exception404(String message) {
        super(message);
    }

    public Exception404(String message, Throwable cause) {
        super(message, cause);
    }

    public Exception404(Throwable cause) {
        super(cause);
    }
}
