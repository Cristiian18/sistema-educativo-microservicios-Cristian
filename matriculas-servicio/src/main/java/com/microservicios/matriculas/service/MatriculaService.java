package com.microservicios.matriculas.service;

import com.microservicios.matriculas.dto.*;
import com.microservicios.matriculas.Model.Matricula;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

public interface MatriculaService {
    // Métodos CRUD
    Matricula registrar(Matricula matricula);
    List<Matricula> listar();
    List<Matricula> findAll();
    Optional<Matricula> findById(Long id);  // Cambiado a Optional
    Matricula save(Matricula matricula);
    void deleteById(Long id);

    // Métodos específicos
    Matricula crearMatricula(MatriculaRequest request);
    Matricula_completaDTO convertToMatriculaCompletaDTO(Matricula matricula);
    EstudianteDTO getEstudiante(Long id);    // Para Feign Client
    AsignaturaDTO getAsignatura(Long id);    // Para Feign Client
}