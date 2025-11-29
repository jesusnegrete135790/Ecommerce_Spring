package com.Jesus.Ecommerce.Modelos;


import jakarta.persistence.*;

@Entity
@Table(name ="imagenes_producto")
public class ImagenesProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url_imagen;
    private String texto_alternativo;

    @ManyToOne
    @JoinColumn(name = "producto_id",referencedColumnName = "id",nullable = true)
    private Producto producto;

    public ImagenesProducto() {
    }

    public ImagenesProducto(Integer id, String url_imagen, String texto_alternativo, Producto producto) {
        this.id = id;
        this.url_imagen = url_imagen;
        this.texto_alternativo = texto_alternativo;
        this.producto = producto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getTexto_alternativo() {
        return texto_alternativo;
    }

    public void setTexto_alternativo(String texto_alternativo) {
        this.texto_alternativo = texto_alternativo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
