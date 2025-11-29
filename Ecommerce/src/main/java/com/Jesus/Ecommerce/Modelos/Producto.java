package com.Jesus.Ecommerce.Modelos;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer cantidadStock;

    private LocalDateTime creado;
    private LocalDateTime actualizado;

    @ManyToOne
    @JoinColumn(name = "categoriaId", referencedColumnName = "id",nullable = true)
    private Categoria categoria ;

    @ManyToOne
    @JoinColumn(name = "usuarioId", referencedColumnName = "id",nullable = true)
    private Usuario usuario;

    public Producto() {
    }

    public Producto(Integer id, String nombre, String descripcion, BigDecimal precio, Integer cantidadStock, LocalDateTime creado, LocalDateTime actualizado, Categoria categoria,Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
        this.creado = creado;
        this.actualizado = actualizado;
        this.categoria = categoria;
        this.usuario= usuario;

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getCantidadStock() {
        return cantidadStock;
    }

    public void setCantidadStock(Integer cantidadStock) {
        this.cantidadStock = cantidadStock;
    }

    public LocalDateTime getCreado() {
        return creado;
    }

    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }

    public LocalDateTime getActualizado() {
        return actualizado;
    }

    public void setActualizado(LocalDateTime actualizado) {
        this.actualizado = actualizado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
