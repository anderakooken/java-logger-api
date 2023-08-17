package com.maissabor.services.models.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Jwts;


public class UsuarioServices{
    
    // @Autowired
    // UsuarioRepo userDao;

    // public String Login(String username, String password){
    //     Optional<Usuarios> userOpt = userDao.login(username, password);
    //     if(userOpt.isPresent()){
    //         String token = UUID.randomUUID().toString();
    //         Usuarios user = userOpt.get();

    //         // Jwts.builder().setSubject("TESTEEEEEE").setId("1").addClaims(teste).signWith(key).setExpiration(c.getTime()).compact();
    //         user.setToken(token);
    //         userDao.save(user);
    //         return token;
    //     }
    // }

}
