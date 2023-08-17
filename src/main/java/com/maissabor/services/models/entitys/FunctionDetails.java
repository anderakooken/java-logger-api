package com.maissabor.services.models.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_functions")
@Getter @Setter
public class FunctionDetails implements Serializable{

    private static final long serialVersionUID  = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column
    private String nome;

    @JsonIgnore
    @Column
    private String type;

    @JsonIgnore
    @Column
    private String descricao;

    @JsonIgnore
    @Column
    private Long user_creator;

    @JsonIgnore
    @Column
    private Date dt_lancamento;

    @JsonIgnore
    @Column
    private int status;

}
