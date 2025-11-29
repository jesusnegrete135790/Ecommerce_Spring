package com.Jesus.Ecommerce.Modelos;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Table(name ="carritos")
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime creado;

    @OneToOne
    @JoinColumn(name = "usuarioId",referencedColumnName = "id",nullable = true)
    private Usuario usuario;

    public Carrito() {
    }

    public Carrito(Integer id, LocalDateTime creado, Usuario usuario) {
        this.id = id;
        this.creado = creado;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCreado() {
        return creado;
    }

    public void setCreado(LocalDateTime creado) {
        this.creado = creado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
