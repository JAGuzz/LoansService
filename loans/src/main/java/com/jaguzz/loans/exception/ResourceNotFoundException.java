package com.jaguzz.loans.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resourceName, String fieldName, String fielValue){
        super(String.format("%s not foud with the given input data %s: '%s'", resourceName, fieldName, fielValue));
    }

}
