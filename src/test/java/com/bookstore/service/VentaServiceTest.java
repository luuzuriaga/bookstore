package com.bookstore.service;

import com.bookstore.exception.BadRequestException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Book;
import com.bookstore.model.Cliente;
import com.bookstore.model.Venta;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.ClienteRepository;
import com.bookstore.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({VentaService.class, ClienteService.class, BookService.class})
class VentaServiceTest {

    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    VentaService ventaService;

    private Book book;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        // Creamos un libro con stock = 2
        book = new Book("Clean Code", "Robert C. Martin", 45.0, 2);
        bookRepository.save(book);

        // Creamos un cliente
        cliente = new Cliente("Lucero", "lucero@test.com");
        clienteRepository.save(cliente);
    }

    @Test
    void cannotSellMoreThanStock() {
        // Intentar vender más de lo que hay en stock → debe lanzar BadRequestException
        assertThrows(BadRequestException.class, () ->
                ventaService.registrarVenta(cliente.getId(), book.getId(), 3));
    }

    @Test
    void cannotSellZeroOrNegativeQuantity() {
        assertThrows(BadRequestException.class, () ->
                ventaService.registrarVenta(cliente.getId(), book.getId(), 0));

        assertThrows(BadRequestException.class, () ->
                ventaService.registrarVenta(cliente.getId(), book.getId(), -1));
    }

    @Test
    void cannotSellIfClienteNotFound() {
        assertThrows(ResourceNotFoundException.class, () ->
                ventaService.registrarVenta(999L, book.getId(), 1));
    }

    @Test
    void cannotSellIfBookNotFound() {
        assertThrows(ResourceNotFoundException.class, () ->
                ventaService.registrarVenta(cliente.getId(), 999L, 1));
    }

    @Test
    void saleReducesStockAndSaves() {
        // Venta válida → debe guardarse y descontar stock
        Venta venta = ventaService.registrarVenta(cliente.getId(), book.getId(), 2);

        assertNotNull(venta.getId()); // la venta se guardó
        assertEquals(0,
                bookRepository.findById(book.getId()).get().getStock()); // stock descontado

        // Verificamos que la venta tenga bien los datos relacionados
        assertEquals(cliente.getId(), venta.getCliente().getId());
        assertEquals(book.getId(), venta.getLibro().getId());
        assertEquals(2, venta.getCantidad());
    }

    @Test
    void getAllVentasTest() {
        // Creamos dos ventas
        ventaService.registrarVenta(cliente.getId(), book.getId(), 1);
        book.setStock(1);
        bookRepository.save(book);
        ventaService.registrarVenta(cliente.getId(), book.getId(), 1);

        List<Venta> ventas = ventaService.getAllVentas();
        assertEquals(2, ventas.size());
    }

    @Test
    void getByIdFound() {
        Venta venta = ventaService.registrarVenta(cliente.getId(), book.getId(), 1);

        Venta found = ventaService.getById(venta.getId());
        assertEquals(venta.getId(), found.getId());
    }

    @Test
    void getByIdNotFound() {
        assertThrows(ResourceNotFoundException.class, () ->
                ventaService.getById(999L));
    }
}