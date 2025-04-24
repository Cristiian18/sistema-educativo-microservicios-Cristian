package com.sistemaeducativo.usuarios.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("ESTUDIANTE")
public class Estudiante extends Usuario {
    private String carrera;
    private Integer semestre;
}