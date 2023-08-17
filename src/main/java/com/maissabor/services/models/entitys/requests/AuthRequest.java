package com.maissabor.services.models.entitys.requests;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthRequest {

    @Email
    @Length(min = 5, max=255)
    private String email;
    @Length(min = 5)
    private String password;
}
