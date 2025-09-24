package com.bookstore.service;

import com.bookstore.exception.BadRequestException;
import com.bookstore.model.Cliente;
import com.bookstore.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(ClienteService.class)
class ClienteServiceTest {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ClienteService clienteService;

    @Test
    void uniqueEmailValidation() {
        Cliente c1 = new Cliente("Juan", "a@a.com");
        clienteService.saveCliente(c1);

        Cliente c2 = new Cliente("Pedro", "a@a.com");

        // ✅ Debe lanzar BadRequestException si el email ya existe
        assertThrows(BadRequestException.class, () -> clienteService.saveCliente(c2));
    }

    @Test
    void saveClienteAndRetrieve() {
        Cliente c = new Cliente("Lucero", "lucero@mail.com");
        Cliente saved = clienteService.saveCliente(c);

        assertNotNull(saved.getId());                // ✅ Se generó un ID
        assertEquals("Lucero", saved.getNombre());   // ✅ Nombre guardado
        assertEquals("lucero@mail.com", saved.getEmail()); // ✅ Email guardado

        // Además, verificar que sí se guarda en la BD
        Cliente fromDb = clienteRepository.findById(saved.getId()).orElse(null);
        assertNotNull(fromDb);
        assertEquals("Lucero", fromDb.getNombre());
        assertEquals("lucero@mail.com", fromDb.getEmail());
    }
}