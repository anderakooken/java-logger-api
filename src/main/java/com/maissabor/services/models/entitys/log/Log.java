package com.maissabor.services.models.entitys.log;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name="tb_log")
@Getter @Setter @ToString
public class Log implements Serializable{

    private static final long serialVersionUID  = 454L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(optional = false, fetch = FetchType.EAGER, targetEntity = Programa.class)
    @JoinColumn(name = "id_program")
    private Programa programa;

    @Column
    private int tipo;

    @Column
    private String descricao;

    @Column
    private String machine;

    @Column
    private String ip;

    @Column
    private Date date_event;

    @Column
    private String usuario_os;

    @Column
    private String usuario_app;

    @Column
    private String file_name;

    @JsonIgnore
    @Column
    private Date dt_lancamento = new Date();

}
