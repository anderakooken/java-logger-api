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

@Entity(name="tb_telas_usuario")
@Getter @Setter
public class Telas {
    
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @Column
    private long id_usuario;

    // @JsonIgnore
    // @Column
    // private long id_tela;

    @OneToOne(targetEntity = Tela.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tela")
    private Tela tela;

    @JsonIgnore
    @Column
    private Date dt_cadastro;

    @JsonIgnore
    @Column
    private long id_user_cad;

    @JsonIgnore
    @Column
    private int status;

}
