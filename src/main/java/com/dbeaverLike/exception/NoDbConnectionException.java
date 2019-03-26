package com.dbeaverLike.exception;

public class NoDbConnectionException extends RuntimeException {
    public NoDbConnectionException(String message, Throwable cause){
        super(message,cause);
    }
}
