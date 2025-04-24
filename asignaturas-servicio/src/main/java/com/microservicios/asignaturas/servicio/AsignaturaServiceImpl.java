package com.microservicios.asignaturas.servicio;

import com.microservicios.asignaturas.model.Asignatura;
import com.microservicios.asignaturas.repositorio.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class AsignaturaServiceImpl extends AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    public AsignaturaServiceImpl(AsignaturaRepository asignaturaRepository) {
        super(asignaturaRepository);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asignatura> listarTodas() {
        return asignaturaRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Asignatura> buscarPorId(Long id) {
        return asignaturaRepository.findById(id);
    }

    @Override
    @Transactional
    public Asignatura guardar(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        asignaturaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Asignatura> buscarPorProfesor(Long profesorId) {
        return asignaturaRepository.findByProfesorId(profesorId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Asignatura> buscarPorNombre(String nombre) {
        return asignaturaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existePorId(Long id) {
        return asignaturaRepository.existsById(id);
    }
}
