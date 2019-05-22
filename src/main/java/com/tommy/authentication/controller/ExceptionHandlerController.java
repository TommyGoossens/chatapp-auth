package com.tommy.authentication.controller;

import com.tommy.authentication.exception.DuplicateEntity;
import com.tommy.authentication.exception.PasswordIncorrectException;
import com.tommy.authentication.exception.NotExistingEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(PasswordIncorrectException.class)
    public final ResponseEntity<?> passwordIncorrectHandler(PasswordIncorrectException ex){
        return ResponseEntity.status(ex.getStatus()).body(ex);
    }

    @ExceptionHandler(NotExistingEntity.class)
    public final ResponseEntity<?> entityDoesNotExist(NotExistingEntity ex){
        return ResponseEntity.status(ex.getStatus()).body(ex);
    }

    @ExceptionHandler(DuplicateEntity.class)
    public final ResponseEntity<?> duplicateEntity(DuplicateEntity ex){
        return ResponseEntity.status(ex.getStatus()).body(ex);
    }
}
