package com.maissabor.services.dao.Repository;

import java.lang.StackWalker.Option;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.maissabor.services.models.entitys.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>{
    
    
    // @Query("SELECT u FROM tb_usuarios u where u.email = :email and u.pw = :pw AND u.status = 1")
    // Optional<Usuarios> login(@Param("email") String email, @Param("pw") String pw);
    // Optional findByToken(String token);

    Optional<Usuario> findByEmail(String email);

}
