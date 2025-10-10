package com.bookstore.controller;

import com.bookstore.model.Cliente;
import com.bookstore.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Test
    void getClienteById_ReturnsCliente() throws Exception {
        Cliente cliente = new Cliente("Lucero", "lucero@mail.com");
        cliente.setId(1L);

        when(clienteService.getById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Lucero"))
                .andExpect(jsonPath("$.email").value("lucero@mail.com"));
    }

    @Test
    void getAllClientes_ReturnsList() throws Exception {
        List<Cliente> clientes = Arrays.asList(
                new Cliente("Lucero", "lucero@mail.com"),
                new Cliente("Pedro", "pedro@mail.com")
        );

        when(clienteService.getAllClientes()).thenReturn(clientes);

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Lucero"))
                .andExpect(jsonPath("$[1].nombre").value("Pedro"));
    }

    @Test
    void createCliente_ReturnsCreated() throws Exception {
        Cliente savedCliente = new Cliente("Juan", "juan@mail.com");
        savedCliente.setId(5L);

        // ✅ usamos ArgumentMatchers.any() para que el mock responda sin importar el objeto recibido
        when(clienteService.saveCliente(any(Cliente.class))).thenReturn(savedCliente);

        String requestBody = """
                {
                  "nombre": "Juan",
                  "email": "juan@mail.com"
                }
                """;

        mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.email").value("juan@mail.com"));
    }

    @Test
    void getClienteById_NotFound() throws Exception {
        doThrow(new RuntimeException("Cliente no encontrado"))
                .when(clienteService).getById(999L);

        mockMvc.perform(get("/api/clientes/999"))
                // ✅ más claro: esperamos 404 Not Found
                .andExpect(status().isNotFound());
    }
}
