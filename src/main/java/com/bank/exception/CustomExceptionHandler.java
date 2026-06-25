package com.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;


@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {

        String errorMessage = ex.getMessage();

        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientBalance(InsufficientBalanceException ex) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        Map.of(
                                "timestamp", LocalDateTime.now(),
                                "message", ex.getMessage(),
                                "status", HttpStatus.BAD_REQUEST.value()
                        )
                );
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAccountNotFound(AccountNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        Map.of(
                                "timestamp", LocalDateTime.now(),
                                "message", ex.getMessage(),
                                "status", HttpStatus.NOT_FOUND.value()
                        )
                );
    }
//    @ExceptionHandler(UsernameNotFoundException.class)
//    public ResponseEntity<Object> handleOrderNotFoundException(
//            UsernameNotFoundException ex, WebRequest request) {
//
//        String errorMessage = ex.getMessage();
//
//        ErrorResponse errorResponse = new ErrorResponse(errorMessage);
//        ex.printStackTrace();
//
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}

