package com.Jesus.Ecommerce.Servicios.Impl;

import com.Jesus.Ecommerce.DTOs.Pagos.PagosRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Pagos.PagosResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pagos.PagosSimplesDTO;
import com.Jesus.Ecommerce.Mappers.PagosMapper;
import com.Jesus.Ecommerce.Modelos.Pagos;
import com.Jesus.Ecommerce.Repositorios.PagosRepository;
import com.Jesus.Ecommerce.Servicios.PagosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoServiceImpl implements PagosService {

    @Autowired
    private PagosRepository pagosRepository;
    @Autowired
    private PagosMapper pagosMapper;

    @Override
    public Boolean PagoPaypal(BigDecimal cantidad) {

        //Logica imaginaria de pago

        return true;
    }

    @Override
    public Boolean PagoVisa(BigDecimal cantidad) {
        //Logica imaginaria de pago

        return true;
    }

    @Override
    public Boolean PagoApplePay(BigDecimal cantidad) {

        //Logica imaginaria de pago

        return true;
    }

    @Override
    public Boolean PagoOxxo(BigDecimal cantidad) {

        //Logica imaginaria de pago

        return true;
    }

    @Override
    public Boolean PagoBBVA(BigDecimal cantidad) {

        //Logica imaginaria de pago

        return true;
    }

    @Override
    public List<PagosSimplesDTO> Pagos() {
        return pagosMapper.toDtoList(pagosRepository.findAll());
    }

    @Override
    public PagosResponseDTO pagoId(Integer id) {
        return pagosMapper.toDto(pagosRepository.findById(id).orElseThrow( ));
    }

    @Override
    public PagosResponseDTO CrearPago(PagosRegistroDTO dto) {
        Pagos pagos = pagosMapper.toEntity(dto);

        pagos.setCreado(LocalDateTime.now());

        pagosRepository.save(pagos);


        return pagosMapper.toDto(pagos);
    }
}
