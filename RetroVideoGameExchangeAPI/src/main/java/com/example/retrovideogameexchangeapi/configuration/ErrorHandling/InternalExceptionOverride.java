package com.example.retrovideogameexchangeapi.configuration.ErrorHandling;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class InternalExceptionOverride extends ResponseEntityExceptionHandler {

    /**
     * Exception Handler method if there is an IllegalArgumentException within the REST Service
     * @param iae A IllegalArgumentException that was caused somewhere within the REST Service
     * @return A ResponseEntity Including an Exception Message, HttpHeaders, and a 400 Bad Request Response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionMessage> argumentException(IllegalArgumentException iae) {
        ExceptionMessage em = new ExceptionMessage(iae.getMessage(), 400);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception Handler method if there is an KeyAlreadyExistsException within the REST Service
     * @param kaee A KeyAlreadyExistsException that was caused somewhere within the REST Service
     * @return A ResponseEntity Including an Exception Message, HttpHeaders, and a 400 Bad Request Response
     */
    @ExceptionHandler(KeyAlreadyExistsException.class)
    public final ResponseEntity<ExceptionMessage> alreadyExistsException(KeyAlreadyExistsException kaee) {
        ExceptionMessage em = new ExceptionMessage(kaee.getMessage(), 400);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception Handler method if there is an SecurityException within the REST Service
     * @param se A SecurityException that was caused somewhere within the REST Service
     * @return A ResponseEntity Including an Exception Message, HttpHeaders, and a 401 Unauthorized Response
     */
    @ExceptionHandler(SecurityException.class)
    public final ResponseEntity<ExceptionMessage> securityException(SecurityException se) {
        ExceptionMessage em = new ExceptionMessage(se.getMessage(), 401);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Exception Handler method if there is an EntityNotFoundException within the REST Service
     * @param enfe A EntityNotFoundException that was caused somewhere within the REST Service
     * @return A ResponseEntity Including an Exception Message, HttpHeaders, and a 400 Bad Request Response
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ExceptionMessage> entityNotFound(EntityNotFoundException enfe) {
        ExceptionMessage em = new ExceptionMessage(enfe.getMessage(), 400);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception Handler method if there is an NullPointerException within the REST Service
     * @param npe A NullPointer Exception that was caused somewhere within the REST Service
     * @return A ResponseEntity Including an Exception Message, HttpHeaders, and a 400 Bad Request Response
     */
    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<ExceptionMessage> entityNotFound(NullPointerException npe) {
        ExceptionMessage em = new ExceptionMessage(npe.getMessage(), 400);
        return new ResponseEntity<>(em, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
