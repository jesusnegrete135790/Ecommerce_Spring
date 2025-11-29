package com.Jesus.Ecommerce.Exepciones;

import com.Jesus.Ecommerce.DTOs.Exepciones.ExepcionesResponse;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoVacioExeption;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaNoEncontradaExeption;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaPadreInvalida;
import com.Jesus.Ecommerce.Exepciones.ItemCarritoExeptions.ItemCarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.PagoExeptions.PagoRechazadoException;
import com.Jesus.Ecommerce.Exepciones.PedidoExeptions.PedidoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.ProductoNoEncontradoExeption;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.StockMenorACero;
import com.Jesus.Ecommerce.Exepciones.UsuarioExeptions.UsuarioNoEncontradoExepcion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNoEncontradoExepcion.class)
    public ResponseEntity<ExepcionesResponse>handleUsuarioNoEncontrado(UsuarioNoEncontradoExepcion exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Usuario no encontrado",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoriaNoEncontradaExeption.class)
    public ResponseEntity<ExepcionesResponse>handleCategoriaNoEncontrada(CategoriaNoEncontradaExeption exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Usuario no encontrado",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductoNoEncontradoExeption.class)
    public ResponseEntity<ExepcionesResponse>handleProductoNoEncontrado(ProductoNoEncontradoExeption exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "producto no encontrado",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(StockMenorACero.class)
    public ResponseEntity<ExepcionesResponse>handleStockMenorACero(StockMenorACero exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "La cantidad de Stock seleccionado no es valido",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CarritoNoEncontrado.class)
    public ResponseEntity<ExepcionesResponse>handleCarritoNoEncontrado(CarritoNoEncontrado exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Carrito no encontrado",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CarritoVacioExeption.class)
    public ResponseEntity<ExepcionesResponse>handleCarritoVacio(CarritoVacioExeption exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "El carrito se encuentra vacion",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PedidoNoEncontrado.class)
    public ResponseEntity<ExepcionesResponse>handlePedidoNoEncontrado(PedidoNoEncontrado exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Pedido no encontrado",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ItemCarritoNoEncontrado.class)
    public ResponseEntity<ExepcionesResponse>handleItemCarritoNoEncontrado(ItemCarritoNoEncontrado exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "ItemCarrito no encontrado",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(CategoriaPadreInvalida.class)
    public ResponseEntity<ExepcionesResponse>handleCategoriaPadreInvalida(CategoriaPadreInvalida exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Categoria invalida",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PagoRechazadoException.class)
    public ResponseEntity<ExepcionesResponse>handlePagoRechazadoException(PagoRechazadoException exeption){

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Pago no realizado",
                exeption.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExepcionesResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();

        // Extraer cada campo que falló y su mensaje
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String campo = ((FieldError) error).getField();
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        // Convertimos el mapa a un String para tu DTO de respuesta (o puedes cambiar tu DTO para aceptar Map)
        String mensajeGeneral = errores.toString();

        ExepcionesResponse response = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de Validación",
                mensajeGeneral
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExepcionesResponse> handleGlobalException(Exception exception) {
        // IMPORTANTE: Aquí NO le mostramos el detalle técnico al usuario por seguridad
        // Solo le decimos "Error interno".
        ExepcionesResponse error = new ExepcionesResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Error Interno del Servidor",
                "Ocurrió un error inesperado, contacte al soporte."
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
