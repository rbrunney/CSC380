package com.example.retrovideogameexchangeapi.util.ErrorHandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.openmbean.KeyAlreadyExistsException;

@ControllerAdvice
public class InternalExceptionOverride extends ResponseEntityExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionMessage> argumentException(IllegalArgumentException iae) {
        ExceptionMessage em = new ExceptionMessage(iae.getMessage(), 400);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(KeyAlreadyExistsException.class)
    public final ResponseEntity<ExceptionMessage> alreadyExistsException(KeyAlreadyExistsException kaee) {
        ExceptionMessage em = new ExceptionMessage(kaee.getMessage(), 400);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SecurityException.class)
    public final ResponseEntity<ExceptionMessage> securityException(SecurityException se) {
        ExceptionMessage em = new ExceptionMessage(se.getMessage(), 401);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}
