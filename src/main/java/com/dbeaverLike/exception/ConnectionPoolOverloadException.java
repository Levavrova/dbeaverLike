package com.dbeaverLike.exception;

public class ConnectionPoolOverloadException extends RuntimeException {
    public ConnectionPoolOverloadException(String msg){
        super(msg);
    }
}
