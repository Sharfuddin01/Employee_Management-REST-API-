package com.spring.exception;

public class RecordNotFoundException extends RuntimeException {
    public RecordNotFoundException(String massege){
        super(massege);
    }
}
