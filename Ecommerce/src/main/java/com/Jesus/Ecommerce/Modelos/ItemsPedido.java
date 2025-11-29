package com.Jesus.Ecommerce.Modelos;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "items_pedido")
public class ItemsPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cantidad;
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "pedidoId",referencedColumnName ="id",nullable = true)
    private Pedidos pedidos;

    @ManyToOne
    @JoinColumn(name = "productoId",referencedColumnName ="id",nullable = true)
    private Producto producto;

    public ItemsPedido() {
    }

    public ItemsPedido(Integer id, Integer cantidad, BigDecimal precio, Pedidos pedidos, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precio = precio;
        this.pedidos = pedidos;
        this.producto = producto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Pedidos getPedidos() {
        return pedidos;
    }

    public void setPedidos(Pedidos pedidos) {
        this.pedidos = pedidos;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
