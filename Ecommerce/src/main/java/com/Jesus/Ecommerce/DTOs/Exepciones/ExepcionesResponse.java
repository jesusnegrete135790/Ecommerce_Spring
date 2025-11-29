package com.Jesus.Ecommerce.DTOs.Exepciones;

import java.time.LocalDateTime;

public record ExepcionesResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String mensaje) {
}
