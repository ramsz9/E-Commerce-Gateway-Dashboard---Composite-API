package com.cmp_products.dto;

/**
 * Respuesta de error del compose. status siempre es "ERROR", igual que el campo status del resumen.
 */
public record ErrorMessageDto(String status, String message) {
}
