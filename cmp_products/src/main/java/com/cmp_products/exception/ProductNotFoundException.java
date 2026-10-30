package com.cmp_products.exception;

/**
 * El catálogo o el inventario respondieron 404 para el id pedido.
 */
public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
