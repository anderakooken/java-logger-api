package com.maissabor.services.models.entitys.token;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maissabor.services.models.entitys.Functions;
import com.maissabor.services.models.entitys.Telas;
import com.maissabor.services.models.entitys.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTTokenService implements TokenInterface{

    private static final Logger log = LoggerFactory.getLogger(JWTTokenService.class); //Váriavel de log
    public static final String JWT_SECRET = "Z-∟??-????C@?2;?|iq*HeO?4→P?f.$??4^9?9??$??T?▲i2??a?et?↕▬?";

    @Autowired
    private HttpServletRequest request;

    public JWTTokenService(){}

    @Override
    public String generateToken(Usuario user) {
        Instant expirationTime = Instant.now().plus(1, ChronoUnit.HOURS);
        Date expirationDate = Date.from(expirationTime);

        Key key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes());

        //Se for um usuário novo, remove as permissões de telas e funções.
        if(user.getPw() == null || user.getPw().isEmpty()){
                user.setTelas(null);
                user.setFunctions(null);
                user.setAdmin(false);
        }

        String compactTokenString = Jwts.builder()
                .claim("id", user.getId())
                .claim("sub", user.getEmail())
                .claim("isenable", user.getStatus())
                .claim("functions", user.getFunctions())
                .claim("telas", user.getTelas())
                .claim("admin", user.isAdmin())
                .claim("user-agent", request.getHeader("User-Agent"))
                // .claim("admin", user.isAdmin())
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return "Bearer " + compactTokenString;
    }

    @Override
    public Usuario parseToken(String token) {
        byte[] secretBytes = JWT_SECRET.getBytes();
        

        try{
                Jws<Claims> jwsClaims = Jwts.parserBuilder()
                        .setSigningKey(secretBytes)
                        .build()
                        .parseClaimsJws(token);

        
               
                Long Id = jwsClaims.getBody()
                        .get("id", Long.class);

                

                String email = jwsClaims.getBody()
                        .getSubject();

                Object objAdmin = jwsClaims.getBody().get("admin");
                boolean admin = false;

                if(objAdmin != null){
                admin = (boolean) objAdmin;
                }

                //Compara o user-agent gravado no token e de quem fez a requisição
                if(!request.getHeader("User-Agent").equals(jwsClaims.getBody().get("user-agent"))){
                        log.warn("User-Agent Diferentes. \r\nAtual:" + request.getHeader("User-Agent") + " - Token: " + jwsClaims.getBody().get("user-agent") + "\r\n :: parseToken()");
                        return new Usuario();
                }

                ObjectMapper mapper = new ObjectMapper();
                List<Functions> functions = new ArrayList<>();
                List<Telas> telas = new ArrayList<>();
                if(jwsClaims.getBody().get("functions") != null){
                        functions = mapper.convertValue(
                                jwsClaims.getBody().get("functions"), 
                                new TypeReference<List<Functions>>(){}
                        );
                }

                if(jwsClaims.getBody().get("telas") != null){
                        telas = mapper.convertValue(
                                jwsClaims.getBody().get("telas"), 
                                new TypeReference<List<Telas>>(){}
                        );
                }
                
        
                return new Usuario(Id, email, functions, telas, admin);

                

                
        }catch(ExpiredJwtException e){
            log.warn("Token Expirado :: parseToken()");
            return new Usuario();
        }
    }
    
}
