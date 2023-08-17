package com.maissabor.services.models.entitys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity(name="tb_telas_grupos")
@Getter @Setter
public class Grupo {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column
    private String id_grupo;

    @Column
    private String grupo;

    @JsonIgnore
    @Column
    private Date dt_cadastro;

    @JsonIgnore
    @Column
    private long id_user;

    @JsonIgnore
    @Column
    private int status;

}
