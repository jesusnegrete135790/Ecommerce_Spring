package com.Jesus.Ecommerce.Mappers;

import com.Jesus.Ecommerce.Modelos.ItemsCarrito;
import com.Jesus.Ecommerce.Modelos.ItemsPedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ItemPedidosPersonalizada {

    // 1. MÉTODO MANUAL (Para la Lista)
    // Escribimos el bucle manualmente para pasar el 'idPedido' a cada elemento
    public List<ItemsPedido> toEntities(List<ItemsCarrito> itemsCarrito, Integer idPedido) {
        if (itemsCarrito == null) {
            return null;
        }

        return itemsCarrito.stream()
                .map(item -> toEntity(item, idPedido)) // Llamamos al método de abajo pasando el ID
                .collect(Collectors.toList());
    }

    // 2. MÉTODO AUTOMÁTICO (Para cada Elemento)
    // MapStruct implementará este método automáticamente
    @Mapping(target = "id", ignore = true) // El ID del nuevo ítem se autogenera en BD
    @Mapping(source = "item.producto.precio", target = "precio") // Guardamos el precio histórico
    @Mapping(source = "item.cantidad", target = "cantidad")
    @Mapping(source = "item.producto", target = "producto")
    // Aquí mapeamos el ID del pedido a la relación
    @Mapping(source = "idPedido", target = "pedidos.id")
    public abstract ItemsPedido toEntity(ItemsCarrito item, Integer idPedido);
}