package com.cmp_products.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.cmp_products.dto.ErrorMessageDto;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFoundExceptionHandler(ProductNotFoundException productNotFoundException) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("ERROR", productNotFoundException.getMessage());
        return new ResponseEntity<>(errorMessageDto, HttpStatus.NOT_FOUND);
    }

    /**
     * Un servicio está apagado o no respondió dentro del tiempo de espera de WebClientConfig.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(WebClientRequestException.class)
    public ResponseEntity<?> serviceUnavailableExceptionHandler(WebClientRequestException requestException) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("ERROR",
                "El servicio " + requestException.getUri() + " no responde");
        return new ResponseEntity<>(errorMessageDto, HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Un servicio respondió, pero con un error distinto de 404 (p. ej. 500).
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<?> serviceErrorExceptionHandler(WebClientResponseException responseException) {
        ErrorMessageDto errorMessageDto = new ErrorMessageDto("ERROR",
                "El servicio " + responseException.getRequest().getURI() + " respondió " + responseException.getStatusCode());
        return new ResponseEntity<>(errorMessageDto, HttpStatus.BAD_GATEWAY);
    }
}
