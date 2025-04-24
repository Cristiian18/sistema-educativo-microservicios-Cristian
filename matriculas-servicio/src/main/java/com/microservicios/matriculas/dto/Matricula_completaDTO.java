package com.microservicios.matriculas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Matricula_completaDTO {
    private Long id;
    private EstudianteDTO estudiante;
    private AsignaturaDTO asignatura;
    private LocalDateTime fechaMatricula;
    private String periodoAcademico;
    private String estado;
}