package com.maissabor.services.models.entitys;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity(name="tb_telas")
@Getter @Setter
public class Tela {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private long id;

    // @JsonIgnore
    // @Column
    // private long id_grupo;

    @OneToOne(targetEntity = Grupo.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    @Column
    private String id_tela;

    @Column
    private String tela;

    @Column
    private String href;
    
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
