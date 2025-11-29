package com.Jesus.Ecommerce.Servicios;

import com.Jesus.Ecommerce.DTOs.ItemPedido.ItemPedidoResponseDTO;
import com.Jesus.Ecommerce.Modelos.ItemsCarrito;

import java.util.List;

public interface ItemPedidoService {

    List<ItemPedidoResponseDTO> crearItemPedidos(Integer id,List<ItemsCarrito> itemsCarritos);

}
