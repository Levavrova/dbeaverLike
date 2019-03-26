package com.dbeaverLike.exception;

public class ConnectionDetailsNotFoundException extends RuntimeException {
    
    public ConnectionDetailsNotFoundException(String msg){
        super(msg);
    }
}
