package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Producto.ProductoRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Producto.ProductoResponseSimpleDTO;
import com.Jesus.Ecommerce.Exepciones.CategoriaExeptions.CategoriaNoEncontradaExeption;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.ProductoNoEncontradoExeption;
import com.Jesus.Ecommerce.Exepciones.ProductoExeptions.StockMenorACero;
import com.Jesus.Ecommerce.Mappers.ProductoMapper;
import com.Jesus.Ecommerce.Modelos.Categoria;
import com.Jesus.Ecommerce.Modelos.Producto;
import com.Jesus.Ecommerce.Repositorios.CategoriasRepository;
import com.Jesus.Ecommerce.Repositorios.ProductoRepository;
import com.Jesus.Ecommerce.Servicios.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public ProductoResponseDTO crearProducto(ProductoRegistroDTO dto) {


        // 2. Buscar la categoría (Esto debe seguir haciéndose aquí para validar que existe en BD)
        // Nota: Asumo que tu DTO tiene dto.categoriaId() o dto.categoria().id() según tu estructura
        Categoria categoria = categoriasRepository.findById(dto.categoriaID())
                .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría no encontrada"));

        // 3. Convertir DTO a Entidad usando el Mapper
        Producto nuevoProducto = productoMapper.toEntity(dto);

        // 4. Asignar datos que no vienen en el DTO o relaciones
        nuevoProducto.setCategoria(categoria);
        nuevoProducto.setCreado(LocalDateTime.now());
        nuevoProducto.setActualizado(LocalDateTime.now());

        // 5. Guardar y devolver convertido a DTO
        Producto guardado = productoRepository.save(nuevoProducto);
        return productoMapper.toDto(guardado);
    }

    @Override
    public ProductoResponseDTO actualizarProducto(Integer idProducto, ProductoRegistroDTO dto) {

        // 1. Buscar producto existente
        Producto productoExistente = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ProductoNoEncontradoExeption("Producto no encontrado"));

        // 2. Buscar nueva categoría
        Categoria categoria = categoriasRepository.findById(dto.categoriaID())
                .orElseThrow(() -> new CategoriaNoEncontradaExeption("Categoría no encontrada"));

        // 3. Actualizar campos automáticamente usando el Mapper
        // Esto reemplaza todos los setNombre, setDescripcion, setPrecio...
        productoMapper.updateProductFromDto(dto, productoExistente);

        // 4. Asignar manualmente lo que ignoramos en el mapper
        productoExistente.setCategoria(categoria);
        productoExistente.setActualizado(LocalDateTime.now());

        // 5. Guardar y devolver DTO
        Producto actualizado = productoRepository.save(productoExistente);
        return productoMapper.toDto(actualizado);
    }

    @Override
    public ProductoResponseDTO obtenerProductoPorId(Integer idProducto) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ProductoNoEncontradoExeption("Producto no encontrado"));

        // Cambio clave: Usar el mapper en lugar del método manual
        return productoMapper.toDto(producto);
    }

    @Override
    public List<ProductoResponseDTO> getAllProducts() {
        return productoMapper.toDtoList(productoRepository.findAll());
    }

    @Override
    public void eliminarProducto(Integer idProducto) {
        if (!productoRepository.existsById(idProducto)) {
            throw new ProductoNoEncontradoExeption("Producto no encontrado con id: " + idProducto);
        }
        productoRepository.deleteById(idProducto);
    }


    @Override
    public ProductoResponseDTO actualizarStock(Integer idProducto, int stock) {
        if (stock < 0) {
            throw new StockMenorACero("La cantidad debe ser mayor o igual a 0");
        }
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ProductoNoEncontradoExeption("Producto no encontrado"));
        producto.setCantidadStock(stock);
        productoRepository.save(producto);
        return productoMapper.toDto(producto);
    }

    @Override
    public List<ProductoResponseSimpleDTO> ordenarCategoria(Integer idCategoria){
        List<Producto> productos = productoRepository.findByCategoriaId(idCategoria);
        return productoMapper.toSimpleDtoList(productos);

    }

    @Override
    public List<ProductoResponseSimpleDTO> ordenarNombre(String nombre){
        List<Producto> productos = productoRepository.findByNombreContaining(nombre);
        return productoMapper.toSimpleDtoList(productos);
    }

    @Override
    public List<ProductoResponseSimpleDTO> ordenarDescripcion(String descripcion){
        List<Producto> productos = productoRepository.findByDescripcionContaining(descripcion);
        return productoMapper.toSimpleDtoList(productos);
    }


}