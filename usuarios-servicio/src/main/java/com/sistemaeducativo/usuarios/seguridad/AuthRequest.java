package com.sistemaeducativo.usuarios.seguridad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
    private String token;
    private String email;
    private String nombre;
    private String telefono;
    private String rol;

    // Solo para estudiantes
    private String carrera;
    private Integer semestre;

    // Solo para profesores
    private String especialidad;
    private String departamento;
}
