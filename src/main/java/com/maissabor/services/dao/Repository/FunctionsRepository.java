package com.maissabor.services.dao.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maissabor.services.models.entitys.Functions;

public interface FunctionsRepository extends JpaRepository<Functions, Long>{
    
    // List<Permitions> findByUser(String id_user);

}
