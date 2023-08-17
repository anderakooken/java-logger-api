package com.maissabor.services.models.entitys;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_functions_usuario")
@Getter @Setter
public class Functions implements Serializable{

    private static final long serialVersionUID  = 1L;

    @JsonIgnore
    @Id 
    @GeneratedValue(strategy=GenerationType.AUTO) 
    private Long id;

    @JsonIgnore
    @Column
    private Long id_user;

    @JsonIgnore
    @Column
    private Date dt_lancamento;

    @JsonIgnore
    @Column
    private int status;

    @OneToOne(targetEntity = FunctionDetails.class, optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_function")
    private FunctionDetails function;

}
