package com.maissabor.services.models.entitys.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthResponse {
    
    private String email;
    private String ip;
    private String acessToken;
    private boolean noHasPw = false;

    public AuthResponse(){}

    public AuthResponse(String email, String ip, String acessToken, boolean noHasPw){
        this.email = email;
        this.acessToken = acessToken;
        this.ip = ip;
        this.noHasPw = noHasPw;
    }
}
