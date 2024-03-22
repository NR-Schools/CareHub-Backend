package com.it120p.carehub.controller;

import com.it120p.carehub.exceptions.BaseException;
import com.it120p.carehub.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerException {
    @ExceptionHandler(value = BaseException.class)
    public ResponseEntity<ErrorResponse> handleDefinedExceptions(
            BaseException ex
    ) {
        return ResponseEntity
                .status(ex.getStatusCode())
                .body(
                        ErrorResponse
                                .builder()
                                .errorSource(ex.getExceptionType())
                                .errorMessage(ex.getMessage())
                                .build()
                );
    }
}
