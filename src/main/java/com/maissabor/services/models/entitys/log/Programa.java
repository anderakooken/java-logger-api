package com.maissabor.services.models.entitys.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "tb_programa")
@Getter @Setter @ToString
public class Programa  implements Serializable{
    
    private static final long serialVersionUID  = 54161L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String nome;

    @JsonIgnore
    @Column
    private int status;

    @JsonIgnore
    @Column
    private Date dt_lancamento = new Date();

}
