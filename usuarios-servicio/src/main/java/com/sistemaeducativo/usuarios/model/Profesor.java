package com.sistemaeducativo.usuarios.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("PROFESOR")
public class Profesor extends Usuario {
    private String especialidad;
    private String departamento;
}