package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pedido.PedidoResponseSimpleDTO;
import com.Jesus.Ecommerce.Servicios.Impl.PedidosServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController

public class PedidosControler {

    @Autowired
    private PedidosServiceImpl pedidosServiceImpl;


    @GetMapping("/pedidos")
    public ResponseEntity<List<PedidoResponseSimpleDTO>> obtenerPedidos(){
        return ResponseEntity.ok(pedidosServiceImpl.listarTodosLosPedidos()) ;
    }

    @GetMapping("/pedidos/{id}")
    public ResponseEntity<PedidoResponseDTO> obtenerPedidosID(@PathVariable Integer id){
        return ResponseEntity.ok(pedidosServiceImpl.obtenerPedidoPorId(id)) ;
    }


    @PostMapping("/pedidos/{UsuarioId}")
    public ResponseEntity<PedidoResponseDTO> AñadirPedido(@Validated @PathVariable Integer UsuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidosServiceImpl.realizarPedido(UsuarioId));
    }


    @PutMapping("/pedidos/{id}/{estado}")
    public ResponseEntity<PedidoResponseDTO>ModificarPedidos(@Validated @PathVariable int id,@Validated @PathVariable String estado){
        return ResponseEntity.ok(pedidosServiceImpl.actualizarEstado(id,estado));
    }

    @DeleteMapping("/pedidos/{id}")
    public ResponseEntity<Void>borrarPedido(@RequestParam int id){
        pedidosServiceImpl.cancelarPedido(id);
        return ResponseEntity.noContent().build();
    }

}
