package com.Jesus.Ecommerce.Servicios;

import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseSimpleDTO;

import java.util.List;


public interface PedidosService {

    PedidoResponseDTO realizarPedido(Integer idUsuario);

    List<PedidoResponseSimpleDTO> obtenerPedidosPorUsuario(Integer idUsuario);

    PedidoResponseDTO obtenerPedidoPorId(Integer idPedido);

    List<PedidoResponseSimpleDTO> listarTodosLosPedidos();

    PedidoResponseDTO actualizarEstado(Integer idPedido, String nuevoEstado);

    void cancelarPedido(Integer idPedido);
}
