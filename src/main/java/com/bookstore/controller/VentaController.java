package com.bookstore.controller;

import com.bookstore.model.Venta;
import com.bookstore.service.VentaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    private final VentaService ventaService;

    public VentaController(VentaService ventaService) { this.ventaService = ventaService; }

    // ejemplo de body: { "clienteId":1, "libroId":2, "cantidad":1 }
    @PostMapping
    public ResponseEntity<Venta> create(@RequestBody Map<String, Object> body) {
        Long clienteId = Long.valueOf(String.valueOf(body.get("clienteId")));
        Long libroId = Long.valueOf(String.valueOf(body.get("libroId")));
        int cantidad = Integer.parseInt(String.valueOf(body.get("cantidad")));
        return ResponseEntity.ok(ventaService.registrarVenta(clienteId, libroId, cantidad));
    }

    @GetMapping
    public List<Venta> listAll() { return ventaService.getAllVentas(); }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> get(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.getById(id));
    }
}