package com.bookstore.controller;

import com.bookstore.model.Cliente;
import com.bookstore.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) { this.clienteService = clienteService; }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.saveCliente(cliente));
    }

    @GetMapping
    public List<Cliente> listAll() { return clienteService.getAllClientes(); }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> get(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}