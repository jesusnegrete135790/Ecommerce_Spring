package com.Jesus.Ecommerce.Exepciones.ProductoExeptions;

public class ProductoNoEncontradoExeption extends RuntimeException{

    public ProductoNoEncontradoExeption(String mensaje){
        super(mensaje);
    }
}
