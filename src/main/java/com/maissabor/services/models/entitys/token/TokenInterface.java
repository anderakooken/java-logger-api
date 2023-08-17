package com.maissabor.services.models.entitys.token;

import com.maissabor.services.models.entitys.Usuario;

public interface TokenInterface {
   
    String generateToken(Usuario user);

    Usuario parseToken(String token);
    
}
