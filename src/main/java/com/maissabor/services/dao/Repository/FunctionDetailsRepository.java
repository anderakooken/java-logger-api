package com.maissabor.services.dao.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maissabor.services.models.entitys.FunctionDetails;

public interface FunctionDetailsRepository extends JpaRepository<FunctionDetails,Long>{
    
    // @Query("SELECT t FROM tb_functions t WHERE t.id in (:IDs)")
    // List<PermitionDetails> findByIn(@Param("@IDs") String IDs);

}
