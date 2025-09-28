package com.bookstore.service;

import com.bookstore.exception.BadRequestException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.model.Cliente;
import com.bookstore.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) { this.clienteRepository = clienteRepository; }

    // regla: email único antes de guardar
    public Cliente saveCliente(Cliente cliente) {
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            throw new BadRequestException("El email es obligatorio");
        }
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new BadRequestException(" Ya existe un cliente con ese email. ");
        }
        return clienteRepository.save(cliente);
    }

    public List<Cliente> getAllClientes() { return clienteRepository.findAll(); }

    public Cliente getById(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado"));
    }

    public void deleteById(Long id) { clienteRepository.deleteById(id); }
}