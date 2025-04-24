package com.microservicios.matriculas.service;

import com.microservicios.matriculas.client.AsignaturaFeignClient;
import com.microservicios.matriculas.client.UsuarioFeignClient;
import com.microservicios.matriculas.dto.AsignaturaDTO;
import com.microservicios.matriculas.dto.EstudianteDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ServiciosConnectionTester {

    private final UsuarioFeignClient usuarioFeignClient;
    private final AsignaturaFeignClient asignaturaFeignClient;

    @Value("${security.token.asignaturas}")
    private String tokenAsignaturas;

    public ServiciosConnectionTester(
            UsuarioFeignClient usuarioFeignClient,
            AsignaturaFeignClient asignaturaFeignClient) {
        this.usuarioFeignClient = usuarioFeignClient;
        this.asignaturaFeignClient = asignaturaFeignClient;
    }

    public void testUsuariosConnection(Long estudianteId) {
        try {
            log.info("Probando conexión con servicio de usuarios...");
            EstudianteDTO estudiante = usuarioFeignClient.getEstudiante(estudianteId);
            log.info("Conexión exitosa con servicio de usuarios. Datos del estudiante: {}", estudiante);
        } catch (Exception e) {
            log.error("Error conectando con servicio de usuarios: {}", e.getMessage());
            throw new RuntimeException("Error en la conexión con servicio de usuarios", e);
        }
    }

    public void testAsignaturasConnection(Long asignaturaId) {
        try {
            log.info("Probando conexión con servicio de asignaturas...");
            AsignaturaDTO asignatura = asignaturaFeignClient.obtenerAsignatura(asignaturaId, tokenAsignaturas);
            log.info("Conexión exitosa con servicio de asignaturas. Datos de la asignatura: {}", asignatura);
        } catch (Exception e) {
            log.error("Error conectando con servicio de asignaturas: {}", e.getMessage());
            throw new RuntimeException("Error en la conexión con servicio de asignaturas", e);
        }
    }

    public void testReducirCupo(Long asignaturaId) {
        try {
            log.info("Probando endpoint de reducir cupo...");
            asignaturaFeignClient.reducirCupo(asignaturaId, tokenAsignaturas);
            log.info("Reducción de cupo exitosa para la asignatura ID: {}", asignaturaId);
        } catch (Exception e) {
            log.error("Error al reducir cupo: {}", e.getMessage());
            throw new RuntimeException("Error al reducir cupo", e);
        }
    }
}
