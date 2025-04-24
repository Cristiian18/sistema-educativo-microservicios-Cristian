package com.microservicios.matriculas.client;

import com.microservicios.matriculas.dto.EstudianteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "servicio-usuarios")
public interface UsuarioFeignClient {
    @GetMapping("/api/estudiantes/{id}")
    EstudianteDTO getEstudiante(@PathVariable Long id);
}
