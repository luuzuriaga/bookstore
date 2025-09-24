package com.bookstore.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Book libro;

    private int cantidad;

    private LocalDateTime fecha = LocalDateTime.now();

    public Venta() {}

    public Venta(Cliente cliente, Book libro, int cantidad) {
        this.cliente = cliente;
        this.libro = libro;
        this.cantidad = cantidad;
    }

    // getters & setters
    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Book getLibro() { return libro; }
    public void setLibro(Book libro) { this.libro = libro; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}