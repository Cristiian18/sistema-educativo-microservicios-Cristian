package com.sistemaeducativo.usuarios.Usuariorepository;



import com.sistemaeducativo.usuarios.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {}