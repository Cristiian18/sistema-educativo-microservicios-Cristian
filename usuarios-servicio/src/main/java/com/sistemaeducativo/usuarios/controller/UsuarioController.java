package com.sistemaeducativo.usuarios.controller;


import com.sistemaeducativo.usuarios.model.Usuario;
import com.sistemaeducativo.usuarios.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.sistemaeducativo.usuarios.model.Profesor;
import com.sistemaeducativo.usuarios.model.Estudiante;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    // Endpoints para Estudiantes
    @GetMapping("/estudiantes")
    public ResponseEntity<List<Estudiante>> getAllEstudiantes() {
        return ResponseEntity.ok(usuarioService.findAllEstudiantes());
    }

    @GetMapping("/estudiantes/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findEstudianteById(id));
    }

    @PostMapping("/estudiantes")
    public ResponseEntity<Estudiante> createEstudiante(@RequestBody Estudiante estudiante) {
        return ResponseEntity.ok(usuarioService.saveEstudiante(estudiante));
    }

    @DeleteMapping("/estudiantes/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        usuarioService.deleteEstudiante(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoints para Profesores
    @GetMapping("/profesores")
    public ResponseEntity<List<Profesor>> getAllProfesores() {
        return ResponseEntity.ok(usuarioService.findAllProfesores());
    }

    @GetMapping("/profesores/{id}")
    public ResponseEntity<Profesor> getProfesorById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findProfesorById(id));
    }

    @PostMapping("/profesores")
    public ResponseEntity<Profesor> createProfesor(@RequestBody Profesor profesor) {
        return ResponseEntity.ok(usuarioService.saveProfesor(profesor));
    }

    @DeleteMapping("/profesores/{id}")
    public ResponseEntity<Void> deleteProfesor(@PathVariable Long id) {
        usuarioService.deleteProfesor(id);
        return ResponseEntity.noContent().build();
    }
}