package com.kodilla.airporthater.exception;

import com.kodilla.airporthater.exception.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AirportNotFoundException.class)
    public ResponseEntity<Object> handleAirportNotFoundException(AirportNotFoundException exception) {
        return new ResponseEntity<>("Airport: " + exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AirportScoreNotFoundException.class)
    public ResponseEntity<Object> handleAirportScoreNotFoundException(AirportScoreNotFoundException exception) {
        return new ResponseEntity<>("Airport score: " + exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<Object> handleCommentNotFoundException(CommentNotFoundException exception) {
        return new ResponseEntity<>("Comment: " + exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FailedToBlockUserException.class)
    public ResponseEntity<Object> handleFailedToBlockUserException(FailedToBlockUserException exception) {
        return new ResponseEntity<>("Failed to block user: " + exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToCreateAirportException.class)
    public ResponseEntity<Object> handleFailedToCreateAirportException(FailedToCreateAirportException exception) {
        return new ResponseEntity<>("Failed to create airport: " + exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToCreateCommentException.class)
    public ResponseEntity<String> handleFailedToCreateCommentException(FailedToCreateCommentException exception) {
        return new ResponseEntity<>("Failed to create comment: " + exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FailedToCreateUserException.class)
    public ResponseEntity<Object> handleFailedToCreateUserException(FailedToCreateUserException exception) {
        return new ResponseEntity<>("Failed to create user: " + exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        return new ResponseEntity<>("User: " + exception, HttpStatus.NOT_FOUND);
    }
}