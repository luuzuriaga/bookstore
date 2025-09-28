package com.bookstore.controller;

import com.bookstore.model.Cliente;
import com.bookstore.repository.ClienteRepository;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ClienteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        clienteRepository.deleteAll();
    }

    @Test
    void createClienteSuccessfully() throws Exception {
        Cliente newCliente = new Cliente("Juan Perez", "juan@email.com");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.email").value("juan@email.com"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void createClienteWithDuplicateEmailFails() throws Exception {
        Cliente cliente1 = new Cliente("Cliente 1", "duplicate@email.com");
        clienteRepository.save(cliente1);

        Cliente cliente2 = new Cliente("Cliente 2", "duplicate@email.com");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente2)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllClientesReturnsCorrectList() throws Exception {
        clienteRepository.save(new Cliente("Cliente 1", "cliente1@email.com"));
        clienteRepository.save(new Cliente("Cliente 2", "cliente2@email.com"));
        clienteRepository.save(new Cliente("Cliente 3", "cliente3@email.com"));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].nombre").value("Cliente 1"))
                .andExpect(jsonPath("$[1].nombre").value("Cliente 2"))
                .andExpect(jsonPath("$[2].nombre").value("Cliente 3"));
    }

    @Test
    void getClienteByIdSuccessfully() throws Exception {
        Cliente savedCliente = clienteRepository.save(new Cliente("Test Cliente", "test@email.com"));

        mockMvc.perform(get("/api/clientes/{id}", savedCliente.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Test Cliente"))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void getClienteByIdNotFound() throws Exception {
        mockMvc.perform(get("/api/clientes/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateClienteSuccessfully() throws Exception {
        Cliente existingCliente = clienteRepository.save(new Cliente("Original Name", "original@email.com"));

        Cliente updatedCliente = new Cliente("Updated Name", "updated@email.com");
        updatedCliente.setId(existingCliente.getId());

        mockMvc.perform(put("/api/clientes/{id}", existingCliente.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Updated Name"))
                .andExpect(jsonPath("$.email").value("updated@email.com"));
    }

    @Test
    void deleteClienteSuccessfully() throws Exception {
        Cliente cliente = clienteRepository.save(new Cliente("To Delete", "delete@email.com"));

        mockMvc.perform(delete("/api/clientes/{id}", cliente.getId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchClienteByEmail() throws Exception {
        clienteRepository.save(new Cliente("John Doe", "john@example.com"));
        clienteRepository.save(new Cliente("Jane Doe", "jane@example.com"));
        clienteRepository.save(new Cliente("Bob Smith", "bob@different.com"));

        mockMvc.perform(get("/api/clientes/search")
                .param("email", "john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void validateEmailFormat() throws Exception {
        Cliente invalidEmailCliente = new Cliente("Invalid Email", "not-an-email");

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidEmailCliente)))
                .andExpect(status().isBadRequest());
    }
}