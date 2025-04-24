package com.microservicios.matriculas.controller;

import com.microservicios.matriculas.service.ServiciosConnectionTester;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/matriculas/test")
@Slf4j
public class TestController {

    private final ServiciosConnectionTester connectionTester;

    public TestController(ServiciosConnectionTester connectionTester) {
        this.connectionTester = connectionTester;
    }

    @PreAuthorize("isAuthenticated()")  // Asegura que el usuario esté autenticado
    @GetMapping("/conexiones")
    public ResponseEntity<Map<String, String>> testConexiones(
            @RequestParam Long estudianteId,
            @RequestParam Long asignaturaId) {
        Map<String, String> resultados = new HashMap<>();

        try {
            connectionTester.testUsuariosConnection(estudianteId);
            resultados.put("usuarios", "Conexión exitosa");
        } catch (Exception e) {
            resultados.put("usuarios", "Error: " + e.getMessage());
            log.error("Error en prueba de conexión con usuarios", e);
        }

        try {
            connectionTester.testAsignaturasConnection(asignaturaId);
            resultados.put("asignaturas", "Conexión exitosa");
        } catch (Exception e) {
            resultados.put("asignaturas", "Error: " + e.getMessage());
            log.error("Error en prueba de conexión con asignaturas", e);
        }

        return ResponseEntity.ok(resultados);
    }
}
