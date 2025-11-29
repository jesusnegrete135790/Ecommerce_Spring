package com.Jesus.Ecommerce.Modelos;


import jakarta.persistence.*;

@Entity
@Table(name ="items_carrito")
public class ItemsCarrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "carritoId",referencedColumnName ="id",nullable = true)
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "productoId",referencedColumnName ="id",nullable = true)
    private Producto producto;

    public ItemsCarrito() {
    }

    public ItemsCarrito(Integer id, Integer cantidad, Carrito carrito, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.carrito = carrito;
        this.producto = producto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public void setCarrito(Carrito carrito) {
        this.carrito = carrito;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
