package com.Jesus.Ecommerce.Modelos;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pagos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String metodoPago;
    private BigDecimal monto;
    private String estadoPago;
    private LocalDateTime creado;

    @ManyToOne
    @JoinColumn(name = "pedidoId",referencedColumnName = "id",nullable = true)
    private Pedidos pedidos;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public LocalDateTime getCreado() {
        return creado;
    }

    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }

    public Pedidos getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedidos pedidos) {
        this.pedidos = pedidos;
    }
}
