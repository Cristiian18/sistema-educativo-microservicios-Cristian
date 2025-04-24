package com.microservicios.asignaturas.repositorio;

import com.microservicios.asignaturas.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    List<Asignatura> findByProfesorId(Long profesorId);
    List<Asignatura> findByNombreContainingIgnoreCase(String nombre);
}
