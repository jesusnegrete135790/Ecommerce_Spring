package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.CarritoExeptions.CarritoVacioExeption;
import com.Jesus.Ecommerce.Exepciones.PagoExeptions.PagoRechazadoException;
import com.Jesus.Ecommerce.Exepciones.PedidoExeptions.PedidoNoEncontrado;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.StockMenorACero;
import com.Jesus.Ecommerce.Exepciones.UsuarioExeptions.UsuarioNoEncontradoExepcion;
import com.Jesus.Ecommerce.Mappers.ItemPedidosMapper;
import com.Jesus.Ecommerce.Mappers.ItemPedidosPersonalizada;
import com.Jesus.Ecommerce.Mappers.PedidoMapper;
import com.Jesus.Ecommerce.Modelos.*;
import com.Jesus.Ecommerce.Repositorios.*;
import com.Jesus.Ecommerce.Servicios.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PedidosServiceImpl implements PedidosService {

    @Autowired
    private PedidosRepository pedidosRepository;
    @Autowired
    private ItemsPedidoRepository itemsPedidoRepository;
    @Autowired
    private ItemsCarritoRepository itemsCarritoRepository;
    @Autowired
    private PedidoMapper pedidoMapper;
    @Autowired
    private ItemPedidosMapper itemPedidosMapper;
    @Autowired
    private CarritoRepository  carritoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ItemPedidosPersonalizada itemPedidosPersonalizada;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private PagoServiceImpl pagoServiceImpl;

    @Override
    @Transactional
    public PedidoResponseDTO realizarPedido(Integer idUsuario) {

        // 1. Buscar Usuario

        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new UsuarioNoEncontradoExepcion("Usuario no encontrado"));
        System.out.println("Paso 1");

        // 2. Buscar Carrito del Usuario
        Carrito carrito = carritoRepository.findByUsuarioId(idUsuario)
                .orElseThrow(() -> new CarritoNoEncontrado("Carrito no encontrado para el usuario"));
        System.out.println("Paso 2");

        // 3. Obtener Items del Carrito
        List<ItemsCarrito> itemsCarrito = itemsCarritoRepository.findByCarrito(carrito);

        if (itemsCarrito.isEmpty()) {
            throw new CarritoVacioExeption("El carrito está vacío, no se puede realizar el pedido");
        }
        System.out.println("Paso 3");

        // 4. Calcular el Total (Lógica de Negocio)
        // Asumiendo que ItemsCarrito tiene getPrecio() calculado (precio * cantidad) o lo calculas aquí
        BigDecimal total = itemsCarrito.stream()
                .map(item -> item.getProducto().getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(!pagoServiceImpl.PagoBBVA(total)){
            throw new PagoRechazadoException("Pago no realizado");
        }
        // Restar stock
        for (ItemsCarrito item:itemsCarrito){
            Producto producto = item.getProducto();

            if (producto.getCantidadStock()<item.getCantidad()){
                throw new StockMenorACero("Stock insuficiente para el producto: " + producto.getNombre());
            }
            producto.setCantidadStock(producto.getCantidadStock()-item.getCantidad());
            productoRepository.save(producto);
        }
        System.out.println("Paso 4");
        // 5. Crear y Guardar el Pedido
        Pedidos pedido = new Pedidos();
        pedido.setUsuario(usuario);
        pedido.setEstado("Iniciado");
        pedido.setCreado(LocalDateTime.now());
        pedido.setActualizado(LocalDateTime.now()); // Es buena práctica llenar este también
        pedido.setMontoTotal(total); // ¡Importante!

        // save() devuelve la entidad YA con el ID generado
        Pedidos pedidoGuardado = pedidosRepository.save(pedido);


        System.out.println("Paso 5");

        // 6. Convertir ItemsCarrito -> ItemsPedido y Guardar
        // Usamos el mapper corregido (abstract class) que te pasé antes
        List<ItemsPedido> itemsPedido = itemPedidosPersonalizada.toEntities(itemsCarrito, pedidoGuardado.getId());
        itemsPedidoRepository.saveAll(itemsPedido);
        System.out.println("Paso 6");
        // 7. Vaciar el Carrito (¡Crucial!)
        itemsCarritoRepository.deleteByCarrito(carrito);
        System.out.println("Paso 7");
        // 8. Retornar DTO
        return pedidoMapper.toDto(pedidoGuardado);

    }

    @Override
    public List<PedidoResponseSimpleDTO> obtenerPedidosPorUsuario(Integer idUsuario) {
        return pedidoMapper.toSimpleListDto(pedidosRepository.findByUsuarioId(idUsuario));
    }

    @Override
    public PedidoResponseDTO obtenerPedidoPorId(Integer idPedido) {
        Pedidos nuevo = pedidosRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNoEncontrado("Pedido no encontrado"));

        return pedidoMapper.toDto(nuevo);
    }

    @Override
    public List<PedidoResponseSimpleDTO> listarTodosLosPedidos() {
        return pedidoMapper.toSimpleListDto(pedidosRepository.findAll());
    }

    @Override
    public PedidoResponseDTO actualizarEstado(Integer idPedido, String nuevoEstado) {
        Pedidos nuevo = pedidosRepository.findById(idPedido)
                .orElseThrow(() -> new PedidoNoEncontrado("Item no encontrado"));

        nuevo.setEstado(nuevoEstado);
        pedidosRepository.save(nuevo);
        return pedidoMapper.toDto(nuevo);
    }

    @Override
    public void cancelarPedido(Integer idPedido) {
        pedidosRepository.delete(pedidosRepository.findById(idPedido).orElseThrow(() -> new PedidoNoEncontrado("Pedido no encontrado")));
        List<ItemsPedido> itemsPedidos = itemsPedidoRepository.findByPedidosId(idPedido);

        for (ItemsPedido item:itemsPedidos){
            Producto producto = item.getProducto();

            producto.setCantidadStock(producto.getCantidadStock() + item.getCantidad());
            productoRepository.save(producto);
        }

    }
}