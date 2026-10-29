package com.cmp_products.dto;

import java.util.Map;

/**
 * Respuesta de Frankfurter: {"amount":1.0,"base":"USD","date":"...","rates":{"MXN":18.74}}.
 */
public record ExchangeRateDto(
        Map<String, Double> rates) {
}
