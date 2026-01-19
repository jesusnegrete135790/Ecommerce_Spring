package com.Jesus.Ecommerce.Servicios.Impl;


import com.Jesus.Ecommerce.DTOs.Carrito.CarritoResponseDTO;
import com.Jesus.Ecommerce.Exepciones.UsuarioExeptions.UsuarioNoEncontradoExepcion;
import com.Jesus.Ecommerce.Mappers.CarritoMapper;
import com.Jesus.Ecommerce.Modelos.Carrito;
import com.Jesus.Ecommerce.Modelos.Usuario;
import com.Jesus.Ecommerce.Repositorios.CarritoRepository;
import com.Jesus.Ecommerce.Repositorios.UsuarioRepository;
import com.Jesus.Ecommerce.Servicios.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CarritoServiceImpl implements CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;
    @Autowired
    private CarritoMapper carritoMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public CarritoResponseDTO obtenerCarritoPorUsuario(Integer usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNoEncontradoExepcion("Usuario no encontrado"));


        Optional<Carrito> carritoExistente = carritoRepository.findByUsuarioId(usuarioId);

        if (carritoExistente.isPresent()) {

            return carritoMapper.toDto(carritoExistente.get());
        } else {
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setUsuario(usuario);
            Carrito carritoGuardado = carritoRepository.save(nuevoCarrito);
            return carritoMapper.toDto(carritoGuardado);
        }

    }
}
