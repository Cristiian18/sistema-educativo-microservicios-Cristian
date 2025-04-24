package com.sistemaeducativo.usuarios.Usuariorepository;



import com.sistemaeducativo.usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface usuariosrep extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);


    boolean existsByUsername(String username);
}