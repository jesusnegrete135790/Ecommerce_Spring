package com.Jesus.Ecommerce.Controllers;

import com.Jesus.Ecommerce.DTOs.Pagos.PagosRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Pagos.PagosResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pagos.PagosSimplesDTO;
import com.Jesus.Ecommerce.Modelos.Pagos;
import com.Jesus.Ecommerce.Repositorios.PagosRepository;
import com.Jesus.Ecommerce.Servicios.Impl.PagoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagosControler {

    @Autowired
    private PagoServiceImpl pagoServicImpl;

    //
    // //
    // // //  metodos Get
    // //
    //

    @GetMapping("/pagos")
    public ResponseEntity<List<PagosSimplesDTO>> obtenerPago(){
        return ResponseEntity.ok(pagoServicImpl.Pagos());
    }

    @GetMapping("/pagos/{id}")
    public ResponseEntity<PagosResponseDTO> obtenerPagoId(@PathVariable Integer id){
        return ResponseEntity.ok(pagoServicImpl.pagoId(id));
    }

    //
    // //
    // // //  metodos Post
    // //
    //

    @PostMapping("/pagos")
    public ResponseEntity<PagosResponseDTO> AñadirPagos(@Validated @RequestBody PagosRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoServicImpl.CrearPago(dto));
    }



}
