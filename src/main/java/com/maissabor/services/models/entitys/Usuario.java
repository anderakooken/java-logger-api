package com.maissabor.services.models.entitys;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tb_usuarios")
@Getter @Setter
public class Usuario implements Serializable{
    
    private static final long serialVersionUID  = 1L;

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    private Long matricula;

    @Column(length = 255)
    private String nome;

    @Column(length = 255, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = true)
    private String pw; 

    
    @Column
    private Date dt_cadastro = new Date();
    
    @Column
    private int status;

    @Column(name="admin")
    private boolean admin;

    // @OneToMany(targetEntity = PermitionDetails.class, mappedBy = "id", orphanRemoval = false, fetch = FetchType.LAZY)
    // @ManyToOne(targetEntity = Permitions.class, optional = false, fetch = FetchType.LAZY)
    // @JoinColumn(name = "id_permition")

    /**
     * Funções que o usuário tem acesso.
     */
    @OneToMany(targetEntity = Functions.class, cascade = CascadeType.ALL, mappedBy = "id_user")
    private List<Functions> functions;


    /**
     * Telas que o usuário tem acesso.
     */
    @OneToMany(targetEntity = Telas.class, cascade = CascadeType.ALL, mappedBy = "id_usuario")
    private List<Telas> telas;



    public Usuario(){}

    public Usuario(Long id, String email, List<Functions> functions, List<Telas> telas, boolean admin){
        this.id = id; 
        this.email = email;
        this.functions = functions;
        this.admin = admin;
    }

    // public boolean isAdmin(){
    //     if(admin == 1){
    //         return true;
    //     }else{
    //         return false;
    //     }
    // }

}
