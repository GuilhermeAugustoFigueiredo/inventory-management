package com.guilherme.demo.exception.handler;

import com.guilherme.demo.exception.ConflictException;
import com.guilherme.demo.exception.DataNotFoundException;
import com.guilherme.demo.exception.ExceptionBody.ExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<ExceptionBody> DataNotFoundException(DataNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionBody(ex.getDomain(),ex.getMessage(), LocalDateTime.now(), DataNotFoundException.class.getSimpleName(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionBody> ConflictException(ConflictException ex){
        return new ResponseEntity<>(new ExceptionBody(ex.getDomain(), ex.getMessage(), LocalDateTime.now(), ConflictException.class.getSimpleName(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
    }
}
