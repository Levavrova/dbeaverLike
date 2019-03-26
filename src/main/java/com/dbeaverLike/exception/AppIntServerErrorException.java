package com.dbeaverLike.exception;

public class AppIntServerErrorException extends RuntimeException {
    
    public AppIntServerErrorException(String msg){
        super(msg);
    }
}
