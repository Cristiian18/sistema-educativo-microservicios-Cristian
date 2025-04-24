package com.microservicios.matriculas.controller;

import com.microservicios.matriculas.client.AsignaturaFeignClient;
import com.microservicios.matriculas.dto.*;
import com.microservicios.matriculas.Model.Matricula;
import com.microservicios.matriculas.service.MatriculaService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AsignaturaFeignClient asignaturaFeignClient;

    public MatriculaController(MatriculaService matriculaService,
                               AsignaturaFeignClient asignaturaFeignClient) {
        this.matriculaService = matriculaService;
        this.asignaturaFeignClient = asignaturaFeignClient;
    }

    @GetMapping
    public ResponseEntity<List<Matricula_completaDTO>> listarMatriculas() {
        List<Matricula> matriculas = matriculaService.findAll();

        List<Matricula_completaDTO> response = matriculas.stream()
                .map(matricula -> {
                    try {
                        return matriculaService.convertToMatriculaCompletaDTO(matricula);
                    } catch (Exception e) {
                        return new Matricula_completaDTO(
                                matricula.getId(),
                                null,  // Estudiante no disponible
                                null,  // Asignatura no disponible
                                matricula.getFechaMatricula(),
                                matricula.getPeriodoAcademico(),
                                matricula.getEstado()
                        );
                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/reducir-cupo")
    public ResponseEntity<Void> reducirCupo(@PathVariable Long id, @RequestParam("token") String token) {
        if (!token.equals("TOKEN_DE_PRUEBA_ABC123")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        asignaturaFeignClient.reducirCupo(id, "TOKEN_DE_PRUEBA_ABC123");

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Matricula_completaDTO> obtenerMatricula(@PathVariable Long id) {
        Matricula matricula = matriculaService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Matrícula no encontrada"));

        try {
            return ResponseEntity.ok(matriculaService.convertToMatriculaCompletaDTO(matricula));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.FAILED_DEPENDENCY,
                    "Error al obtener datos de servicios externos");
        }
    }

    @PostMapping
    public ResponseEntity<Matricula_completaDTO> crearMatricula(@RequestBody MatriculaRequest request) {
        Matricula matricula = matriculaService.crearMatricula(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(matriculaService.convertToMatriculaCompletaDTO(matricula));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMatricula(@PathVariable Long id) {
        matriculaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}