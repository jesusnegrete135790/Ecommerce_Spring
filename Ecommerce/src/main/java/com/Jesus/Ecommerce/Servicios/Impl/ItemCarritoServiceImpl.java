package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.ItemCarrito.ItemCarritoResponseDTO;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.ItemCarritoExeptions.ItemCarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.ProductoNoEncontradoExeption;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.StockMenorACero;
import com.Jesus.Ecommerce.Mappers.ItemCarritoMapper;
import com.Jesus.Ecommerce.Modelos.Carrito;
import com.Jesus.Ecommerce.Modelos.ItemsCarrito;
import com.Jesus.Ecommerce.Modelos.Producto;
import com.Jesus.Ecommerce.Repositorios.CarritoRepository;
import com.Jesus.Ecommerce.Repositorios.ItemsCarritoRepository;
import com.Jesus.Ecommerce.Repositorios.ProductoRepository;
import com.Jesus.Ecommerce.Servicios.ItemsCarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCarritoServiceImpl implements ItemsCarritoService {
    @Autowired
    private ItemsCarritoRepository itemsCarritoRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private ItemCarritoMapper itemCarritoMapper;


    @Override
    @Transactional
    public ItemCarritoResponseDTO añadir(ItemCarritoRegistroDTO dto) {

        if (dto.cantidad() <= 0) {
            throw new StockMenorACero("La cantidad debe ser mayor a 0");
        }


        Integer idCarrito = dto.carritoID();
        Integer idProducto = dto.productoID();

        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new CarritoNoEncontrado("Carrito no encontrado con id: " + idCarrito));

        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ProductoNoEncontradoExeption("Producto no encontrado con id: " + idProducto));

        // Verificar si ya existe
        Optional<ItemsCarrito> existente = itemsCarritoRepository.findByCarrito_IdAndProducto_Id(idCarrito, idProducto);

        ItemsCarrito itemGuardado;

        if (existente.isPresent()) {
            // Actualizar existente
            ItemsCarrito item = existente.get();
            int nuevaCantidad = item.getCantidad() + dto.cantidad();
            validarStock(producto, nuevaCantidad);
            item.setCantidad(nuevaCantidad);
            itemGuardado = itemsCarritoRepository.save(item);
        } else {
            // Crear nuevo usando Mapper (parcialmente)
            ItemsCarrito nuevoItem = itemCarritoMapper.toEntity(dto);
            // Asignamos relaciones manualmente porque toEntity las ignora para forzar la búsqueda en BD
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProducto(producto);
            validarStock(producto, dto.cantidad());

            itemGuardado = itemsCarritoRepository.save(nuevoItem);
        }

        return itemCarritoMapper.toDto(itemGuardado);
    }


    @Override
    @Transactional
    public ItemCarritoResponseDTO modificar(Integer idItem, ItemCarritoRegistroDTO dto) {
        if (dto.cantidad() <= 0) {
            throw new StockMenorACero("La cantidad debe ser mayor a 0");
        }

        // Buscamos el item específico
        ItemsCarrito item = itemsCarritoRepository.findById(idItem)
                .orElseThrow(() -> new ItemCarritoNoEncontrado("Item no encontrado"));

        // Opcional: Verificar si cambiamos de carrito o producto (generalmente no se hace en modificar, solo cantidad)
        // Si permites cambiar producto, deberías buscarlo de nuevo aquí.

        validarStock(item.getProducto(), dto.cantidad());

        // Actualizamos campos simples desde el DTO
        itemCarritoMapper.updateFromDto(dto, item);

        // Aseguramos consistencia manual si es necesario
        item.setCantidad(dto.cantidad());

        ItemsCarrito guardado = itemsCarritoRepository.save(item);
        return itemCarritoMapper.toDto(guardado);
    }

    @Override
    public ItemCarritoResponseDTO cambiarCantidad(Integer idCarrito, Integer idProducto, int nuevaCantidad) {
        if (nuevaCantidad <= 0) {
            throw new StockMenorACero("La cantidad debe ser mayor a 0");
        }

        ItemsCarrito item = itemsCarritoRepository.findByCarrito_IdAndProducto_Id(idCarrito, idProducto)
                .orElseThrow(() -> new ItemCarritoNoEncontrado("Item no encontrado en el carrito"));

        validarStock(item.getProducto(), nuevaCantidad);

        item.setCantidad(nuevaCantidad);
        ItemsCarrito guardado = itemsCarritoRepository.save(item);

        return itemCarritoMapper.toDto(guardado);
    }

    @Override
    public void eliminar(int id) {
        itemsCarritoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void vaciar(int idCarrito) {
        Carrito carrito = carritoRepository.findById(idCarrito)
                .orElseThrow(() -> new CarritoNoEncontrado("Carrito no encontrado"));
        itemsCarritoRepository.deleteByCarrito(carrito);
    }

    @Override
    public List<ItemCarritoResponseDTO> getAllItemsProducto() {
        List<ItemsCarrito> items = itemsCarritoRepository.findAll();
        return itemCarritoMapper.toListDto(items);
    }

    // --- Método auxiliar para validar stock ---
    private void validarStock(Producto producto, int cantidadRequerida) {
        if (cantidadRequerida < producto.getCantidadStock()) {
            throw new StockMenorACero("La cantidad solicitada excede el stock disponible (" + producto.getCantidadStock() + ")");
        }
    }


}
