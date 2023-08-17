package com.maissabor.services.controller.processos;


import java.security.Key;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maissabor.services.dao.Repository.LogRepository;
import com.maissabor.services.models.entitys.log.Log;
import com.maissabor.services.models.entitys.requests.LogRequest;

/**
 * @apiNote RestController para salvar e listar os logs
 * @see *Url - Método - Função
 * @see /log - POST - saveLog
 * @see /log/search - POST - getLogs
 */
@RestController
@RequestMapping(value = "/log", produces = "application/json")
public class LogService {

    // private static final Logger log = LoggerFactory.getLogger(LogService.class); //Váriavel de log
    

    @Autowired
    private LogRepository logRepo;

    /**
     * @apiNote  Recebe um log e salva o Log no DataBase
     * @param map
     * @return 
     */
    @PostMapping
    public ResponseEntity<?> saveLog(@RequestBody @Valid Log log){
      
        return ResponseEntity.ok(logRepo.save(log));

    }

    /**
     * @apiNote Serviço que retorna todos os logs
     * @param map
     * @return
     */
    @PostMapping("search")
    @PreAuthorize("hasAuthority('GET LOGGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getLogs(){//@RequestBody @Valid LogRequest request
        return ResponseEntity.ok(logRepo.findAll());

    }

}
