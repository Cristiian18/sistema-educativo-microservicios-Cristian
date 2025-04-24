package com.microservicios.asignaturas.servicio;

import com.microservicios.asignaturas.model.Asignatura;
import com.microservicios.asignaturas.repositorio.AsignaturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public abstract class AsignaturaService {
    private final AsignaturaRepository asignaturaRepository;

    public AsignaturaService(AsignaturaRepository asignaturaRepository) {
        this.asignaturaRepository = asignaturaRepository;
    }

    public List<Asignatura> listarTodas() {
        return asignaturaRepository.findAll();
    }

    public Asignatura obtenerPorId(Long id) {
        return asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada"));
    }

    @Transactional(readOnly = true)
    public abstract Optional<Asignatura> buscarPorId(Long id);

    @Transactional
    public Asignatura guardar(Asignatura asignatura) {
        if (asignatura.getId() == null) {
            asignatura.setFechaCreacion(LocalDateTime.now());
        }
        asignatura.setFechaActualizacion(LocalDateTime.now());
        return asignaturaRepository.save(asignatura);
    }

    public List<Asignatura> obtenerAsignaturasPorProfesor(Long profesorId) {
        return asignaturaRepository.findByProfesorId(profesorId);
    }

    public void eliminar(Long id) {
        asignaturaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public abstract List<Asignatura> buscarPorProfesor(Long profesorId);

    @Transactional(readOnly = true)
    public abstract List<Asignatura> buscarPorNombre(String nombre);

    @Transactional(readOnly = true)
    public abstract boolean existePorId(Long id);
}