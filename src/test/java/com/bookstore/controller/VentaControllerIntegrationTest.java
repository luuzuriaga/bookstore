package com.bookstore.controller;

import com.bookstore.model.Book;
import com.bookstore.model.Cliente;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.ClienteRepository;
import com.bookstore.repository.VentaRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class VentaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Book testBook;
    private Cliente testCliente;

    @BeforeEach
    void setUp() {
        ventaRepository.deleteAll();
        bookRepository.deleteAll();
        clienteRepository.deleteAll();

        testBook = bookRepository.save(new Book("Test Book", "Test Author", 25.0, 10));
        testCliente = clienteRepository.save(new Cliente("Test Cliente", "test@email.com"));
    }

    @Test
    void registrarVentaSuccessfully() throws Exception {
        Map<String, Object> ventaRequest = new HashMap<>();
        ventaRequest.put("clienteId", testCliente.getId());
        ventaRequest.put("libroId", testBook.getId());
        ventaRequest.put("cantidad", 3);

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ventaRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidad").value(3))
                .andExpect(jsonPath("$.cliente.id").value(testCliente.getId()))
                .andExpect(jsonPath("$.libro.id").value(testBook.getId()))
                .andExpect(jsonPath("$.fecha").exists());

        mockMvc.perform(get("/api/books/{id}", testBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(7));
    }

    @Test
    void registrarVentaWithInsufficientStock() throws Exception {
        Map<String, Object> ventaRequest = new HashMap<>();
        ventaRequest.put("clienteId", testCliente.getId());
        ventaRequest.put("libroId", testBook.getId());
        ventaRequest.put("cantidad", 15);

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ventaRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registrarVentaWithInvalidQuantity() throws Exception {
        Map<String, Object> ventaRequest = new HashMap<>();
        ventaRequest.put("clienteId", testCliente.getId());
        ventaRequest.put("libroId", testBook.getId());
        ventaRequest.put("cantidad", 0);

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ventaRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registrarVentaWithNonExistentCliente() throws Exception {
        Map<String, Object> ventaRequest = new HashMap<>();
        ventaRequest.put("clienteId", 999L);
        ventaRequest.put("libroId", testBook.getId());
        ventaRequest.put("cantidad", 2);

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ventaRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void registrarVentaWithNonExistentBook() throws Exception {
        Map<String, Object> ventaRequest = new HashMap<>();
        ventaRequest.put("clienteId", testCliente.getId());
        ventaRequest.put("libroId", 999L);
        ventaRequest.put("cantidad", 2);

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ventaRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllVentas() throws Exception {
        Map<String, Object> venta1 = new HashMap<>();
        venta1.put("clienteId", testCliente.getId());
        venta1.put("libroId", testBook.getId());
        venta1.put("cantidad", 1);

        Map<String, Object> venta2 = new HashMap<>();
        venta2.put("clienteId", testCliente.getId());
        venta2.put("libroId", testBook.getId());
        venta2.put("cantidad", 2);

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venta1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venta2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cantidad").value(1))
                .andExpect(jsonPath("$[1].cantidad").value(2));
    }

    @Test
    void getVentaById() throws Exception {
        Map<String, Object> ventaRequest = new HashMap<>();
        ventaRequest.put("clienteId", testCliente.getId());
        ventaRequest.put("libroId", testBook.getId());
        ventaRequest.put("cantidad", 2);

        String response = mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ventaRequest)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Long ventaId = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/api/ventas/{id}", ventaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ventaId))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    void multipleVentasReduceStockCorrectly() throws Exception {
        Map<String, Object> venta1 = new HashMap<>();
        venta1.put("clienteId", testCliente.getId());
        venta1.put("libroId", testBook.getId());
        venta1.put("cantidad", 3);

        Map<String, Object> venta2 = new HashMap<>();
        venta2.put("clienteId", testCliente.getId());
        venta2.put("libroId", testBook.getId());
        venta2.put("cantidad", 5);

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venta1)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/ventas/registrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(venta2)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/books/{id}", testBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(2));
    }
}