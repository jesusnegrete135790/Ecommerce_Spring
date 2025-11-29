package com.Jesus.Ecommerce.Servicios;

import com.Jesus.Ecommerce.DTOs.Pagos.PagosRegistroDTO;
import com.Jesus.Ecommerce.DTOs.Pagos.PagosResponseDTO;
import com.Jesus.Ecommerce.DTOs.Pagos.PagosSimplesDTO;
import com.Jesus.Ecommerce.Modelos.Pagos;

import java.math.BigDecimal;
import java.util.List;

public interface PagosService {

    Boolean PagoPaypal(BigDecimal cantidad);

    Boolean PagoVisa(BigDecimal cantidad);

    Boolean PagoApplePay(BigDecimal cantidad);

    Boolean PagoOxxo(BigDecimal cantidad);

    Boolean PagoBBVA(BigDecimal cantidad);

    List<PagosSimplesDTO>Pagos();

    PagosResponseDTO pagoId(Integer id);

    PagosResponseDTO CrearPago(PagosRegistroDTO dto);

}
