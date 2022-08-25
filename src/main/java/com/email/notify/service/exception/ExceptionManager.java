package com.email.notify.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionManager {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String >> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> valid = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {String field=((FieldError)error).getField();
        String message=error.getDefaultMessage();
        valid.put(field,message);
        });
        return new ResponseEntity<>(valid, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Map<String,String >> invalidEmailException(InvalidEmailException ex){
        Map<String, String> valid = new HashMap<>();
        String message = ex.getMessage();
            valid.put("error","true");
            valid.put("message",message);
        return new ResponseEntity<>(valid, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(InValidContentTypeException.class)
    public ResponseEntity<Map<String,String >> inValidContentTypeException(InValidContentTypeException ex){
        Map<String, String> valid = new HashMap<>();
        String message = ex.getMessage();
            valid.put("error","true");
            valid.put("message",message);
        return new ResponseEntity<>(valid, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<Map<String,String >> fileNotFoundException(FileNotFoundException ex){
        Map<String, String> valid = new HashMap<>();
        String message = ex.getMessage();
            valid.put("error","true");
            valid.put("message",message);
        return new ResponseEntity<>(valid, HttpStatus.BAD_REQUEST);
    }
}

