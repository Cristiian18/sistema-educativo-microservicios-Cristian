package com.sistemaeducativo.usuarios.service;

import com.sistemaeducativo.usuarios.Usuariorepository.EstudianteRepository;
import com.sistemaeducativo.usuarios.Usuariorepository.ProfesorRepository;
import com.sistemaeducativo.usuarios.Usuariorepository.usuariosrep;
import com.sistemaeducativo.usuarios.model.Estudiante;
import com.sistemaeducativo.usuarios.model.Profesor;
import com.sistemaeducativo.usuarios.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private usuariosrep usuarioRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    // Métodos para Usuario
    @Override
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Métodos para Estudiante
    @Override
    public List<Estudiante> findAllEstudiantes() {
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante findEstudianteById(Long id) {
        return estudianteRepository.findById(id).orElse(null);
    }

    @Override
    public Estudiante saveEstudiante(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public void deleteEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }

    // Métodos para Profesor
    @Override
    public List<Profesor> findAllProfesores() {
        return profesorRepository.findAll();
    }

    @Override
    public Profesor findProfesorById(Long id) {
        return profesorRepository.findById(id).orElse(null);
    }

    @Override
    public Profesor saveProfesor(Profesor profesor) {
        return profesorRepository.save(profesor);
    }

    @Override
    public void deleteProfesor(Long id) {
        profesorRepository.deleteById(id);
    }
}
