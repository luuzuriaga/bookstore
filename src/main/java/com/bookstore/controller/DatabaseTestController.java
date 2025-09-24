package com.bookstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DatabaseTestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/test-db")
    public String testDatabaseConnection() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT NOW()", String.class);
            return "✅ Conexión exitosa a MySQL. Hora del servidor: " + result;
        } catch (Exception e) {
            return "❌ Error en la conexión: " + e.getMessage();
        }
    }
}