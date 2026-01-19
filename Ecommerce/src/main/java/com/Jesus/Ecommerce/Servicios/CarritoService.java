package com.Jesus.Ecommerce.Servicios;

import com.Jesus.Ecommerce.DTOs.Carrito.CarritoResponseDTO;

public interface CarritoService {


    CarritoResponseDTO obtenerCarritoPorUsuario(Integer usuarioId);
}

/*
        | Método   | Endpoint               | Descripción                      |
        | -------- | ---------------------- | -------------------------------- |
        | `GET`    | `/cart`                | Obtiene el carrito actual        |
        | `POST`   | `/cart/apply-coupon`   | Aplica un cupón                  |
        | `POST`   | `/cart/checkout`       | Inicia el proceso de pago/pedido |*/