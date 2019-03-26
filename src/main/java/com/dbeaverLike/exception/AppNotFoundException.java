package com.dbeaverLike.exception;

public class AppNotFoundException extends RuntimeException {
    
    public AppNotFoundException(String msg){
        super(msg);
    }
}
