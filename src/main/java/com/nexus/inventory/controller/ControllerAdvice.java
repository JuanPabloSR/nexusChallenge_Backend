package com.nexus.inventory.controller;

import com.nexus.inventory.dtos.error.ErrorDTO;
import com.nexus.inventory.exceptions.RequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<ErrorDTO> requestExceptionHandler(RequestException exception) {
        ErrorDTO error = ErrorDTO.builder().message(exception.getMessage()).status(exception.getStatus()).build();
        return new ResponseEntity<>(error, exception.getStatus());
    }
}
