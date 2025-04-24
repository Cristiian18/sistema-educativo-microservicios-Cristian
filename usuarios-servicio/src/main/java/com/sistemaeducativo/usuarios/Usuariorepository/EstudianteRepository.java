package com.sistemaeducativo.usuarios.Usuariorepository;

import com.sistemaeducativo.usuarios.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {}
