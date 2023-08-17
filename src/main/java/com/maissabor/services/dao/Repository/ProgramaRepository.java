package com.maissabor.services.dao.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maissabor.services.models.entitys.log.Programa;

public interface ProgramaRepository extends JpaRepository<Programa, Long> {
    
}
