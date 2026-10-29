package com.base_inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.base_inventory.dto.ErrorMessageDto;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<?> inventoryNotFoundExceptionHandler(InventoryNotFoundException inventoryNotFoundException) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto(
                inventoryNotFoundException.getMessage(),
                HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity<>(errorMessageDto, HttpStatus.NOT_FOUND);
    }
}
