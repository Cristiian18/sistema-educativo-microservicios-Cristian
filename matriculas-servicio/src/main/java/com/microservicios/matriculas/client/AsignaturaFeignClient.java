package com.microservicios.matriculas.client;

import com.microservicios.matriculas.dto.AsignaturaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "asignaturas-service", url = "${asignaturas.service.url}")
public interface AsignaturaFeignClient {

    @GetMapping("/api/asignaturas/{id}")
    AsignaturaDTO obtenerAsignatura(
            @PathVariable("id") Long id,
            @RequestHeader("X-Token-Asig") String token
    );

    @PutMapping("/api/asignaturas/{id}/reducir-cupo")
    void reducirCupo(
            @PathVariable("id") Long id,
            @RequestHeader("X-Token-Asig") String token
    );
}
