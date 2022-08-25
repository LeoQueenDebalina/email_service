package com.email.notify.service.exception;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(String massage){
        super(massage);
    }
}
