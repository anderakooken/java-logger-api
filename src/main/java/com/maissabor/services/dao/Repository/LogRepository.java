package com.maissabor.services.dao.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maissabor.services.models.entitys.log.Log;

public interface LogRepository extends JpaRepository<Log, Long> {
    
}
