package com.Jesus.Ecommerce.Servicios;

import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoResponseDTO;

import java.util.List;

public interface ItemsCarritoService {
    // Ahora recibe el DTO completo y devuelve la respuesta
    ItemCarritoResponseDTO añadir(ItemCarritoRegistroDTO dto);

    // Modificar cantidad o datos (usando el ID del item o DTO)
    ItemCarritoResponseDTO modificar(Integer idItem, ItemCarritoRegistroDTO dto);

    // Métodos de eliminación (se suelen mantener por ID)
    void eliminar(int id); // O eliminar por idItem si prefieres

    void vaciar(int idCarrito);
    List<ItemCarritoResponseDTO> getAllItemsProducto();

    ItemCarritoResponseDTO cambiarCantidad(Integer idCarrito, Integer idProducto, int nuevaCantidad);


}
