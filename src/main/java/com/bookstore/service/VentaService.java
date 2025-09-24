package com.bookstore.service;

import com.bookstore.exception.BadRequestException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.model.Cliente;
import com.bookstore.model.Venta;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.ClienteRepository;
import com.bookstore.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VentaService {
    private final VentaRepository ventaRepository;
    private final BookRepository bookRepository;
    private final ClienteRepository clienteRepository;

    public VentaService(VentaRepository ventaRepository, BookRepository bookRepository, ClienteRepository clienteRepository) {
        this.ventaRepository = ventaRepository;
        this.bookRepository = bookRepository;
        this.clienteRepository = clienteRepository;
    }

    // regla: validar stock antes de registrar; todo en una transacción
    @Transactional
    public Venta registrarVenta(Long clienteId, Long libroId, int cantidad) {
        if (cantidad <= 0) throw new BadRequestException("Cantidad inválida");

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));

        Book libro = bookRepository.findById(libroId)
                .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado"));

        if (libro.getStock() < cantidad) {
            throw new BadRequestException("Stock insuficiente para el libro: " + libro.getTitle());
        }

        // descontar stock
        libro.setStock(libro.getStock() - cantidad);
        bookRepository.save(libro);

        // crear y guardar venta
        Venta venta = new Venta(cliente, libro, cantidad);
        return ventaRepository.save(venta);
    }

    public List<Venta> getAllVentas() { return ventaRepository.findAll(); }

    public Venta getById(Long id) {
        return ventaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada"));
    }
}