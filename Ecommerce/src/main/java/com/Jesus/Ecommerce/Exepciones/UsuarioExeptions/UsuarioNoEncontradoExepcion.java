package com.Jesus.Ecommerce.Exepciones.UsuarioExeptions;

public class UsuarioNoEncontradoExepcion extends RuntimeException{

    public UsuarioNoEncontradoExepcion(String mensaje) {
        super(mensaje); // Pasamos el mensaje a la clase padre
    }
}
