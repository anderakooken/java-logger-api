package com.maissabor.services.models.entitys.requests;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LogRequest {
    
    @NotNull
    private Date dt_inicio;
    
    @NotNull
    private Date dt_fim;

}
