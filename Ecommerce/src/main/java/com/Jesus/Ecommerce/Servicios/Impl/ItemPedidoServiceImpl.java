package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.ItemPedido.ItemPedidoResponseDTO;
import com.Jesus.Ecommerce.Mappers.ItemPedidosMapper;
import com.Jesus.Ecommerce.Modelos.ItemsCarrito;
import com.Jesus.Ecommerce.Modelos.ItemsPedido;
import com.Jesus.Ecommerce.Repositorios.ItemsCarritoRepository;
import com.Jesus.Ecommerce.Repositorios.ItemsPedidoRepository;
import com.Jesus.Ecommerce.Servicios.ItemPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService {

    @Autowired
    private ItemsCarritoRepository itemsCarritoRepository;

    @Autowired
    private ItemsPedidoRepository itemsPedidoRepository;

    @Autowired
    private ItemPedidosMapper itemPedidosMapper;


    @Override
    public List<ItemPedidoResponseDTO> crearItemPedidos(Integer id,List<ItemsCarrito> itemsCarritos) {


        return List.of();
    }
}
