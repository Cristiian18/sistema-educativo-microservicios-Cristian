package com.sistemaeducativo.usuarios.service;

import com.sistemaeducativo.usuarios.model.Estudiante;
import com.sistemaeducativo.usuarios.model.Profesor;
import com.sistemaeducativo.usuarios.model.Usuario;

import java.util.List;

public interface UsuarioService {

    // Usuarios generales
    List<Usuario> listarTodos();
    Usuario guardarUsuario(Usuario usuario);

    // Estudiantes
    List<Estudiante> findAllEstudiantes();
    Estudiante findEstudianteById(Long id);
    Estudiante saveEstudiante(Estudiante estudiante);
    void deleteEstudiante(Long id);

    // Profesores
    List<Profesor> findAllProfesores();
    Profesor findProfesorById(Long id);
    Profesor saveProfesor(Profesor profesor);
    void deleteProfesor(Long id);
}
