package com.base_products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.base_products.dto.ErrorMessageDto;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFoundExceptionHandler(ProductNotFoundException productNotFoundException) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(
                productNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorMessageDto, HttpStatus.NOT_FOUND);
    }
}
