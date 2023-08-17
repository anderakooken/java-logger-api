package com.maissabor.services.controller.cadastros;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.jasypt.util.password.StrongPasswordEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maissabor.services.dao.Repository.UsuarioRepository;
import com.maissabor.services.models.entitys.Usuario;

@RestController
@RequestMapping(value = "/user", produces = "application/json")
@PreAuthorize("hasAuthority('CRUD USER') or hasRole('ROLE_ADMIN')")
public class UserService {
    
    @Autowired
    private UsuarioRepository userRepo;

    
    @PostMapping("search")
    public List<Usuario> getUsers(){
        return userRepo.findAll();
    }

    @PostMapping("save")
    public Usuario saveUser(@RequestBody @Valid Usuario user){
        return userRepo.save(user);
    }

    @PostMapping("exclude")
    public Usuario excludeUser(@RequestBody Usuario user){
        return userRepo.save(user);
    }

}
