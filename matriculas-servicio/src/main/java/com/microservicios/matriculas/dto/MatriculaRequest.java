package com.microservicios.matriculas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatriculaRequest {
    private Long estudianteId;
    private Long asignaturaId;
    private String periodoAcademico;
}