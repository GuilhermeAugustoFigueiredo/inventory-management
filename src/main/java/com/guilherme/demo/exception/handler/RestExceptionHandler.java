package com.guilherme.demo.exception.handler;

import com.guilherme.demo.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    private ResponseEntity<String> entityNotFoundHandler(EntityNotFoundException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entidade não encontrada!");
    }
}
