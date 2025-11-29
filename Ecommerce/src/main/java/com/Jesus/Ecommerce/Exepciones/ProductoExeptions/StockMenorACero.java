package com.Jesus.Ecommerce.Exepciones.ProductoExeptions;

public class StockMenorACero extends RuntimeException {
    public StockMenorACero(String message) {
        super(message);
    }
}
