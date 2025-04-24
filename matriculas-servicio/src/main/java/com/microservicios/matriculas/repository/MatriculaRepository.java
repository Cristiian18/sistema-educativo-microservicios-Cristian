package com.microservicios.matriculas.repository;

import com.microservicios.matriculas.Model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
}