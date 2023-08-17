package com.maissabor.services.models.entitys.token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.maissabor.services.models.entitys.Usuario;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTTokenService tokenService;

    public JwtAuthenticationFilter(JWTTokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            String authorizationHeader = request.getHeader("Authorization");

            if (authorizationHeaderIsInvalid(authorizationHeader)) {
                filterChain.doFilter(request, response);
                return;
            }
    
            UsernamePasswordAuthenticationToken token = createToken(authorizationHeader);
    
            SecurityContextHolder.getContext().setAuthentication(token);
            filterChain.doFilter(request, response);
    }

    private boolean authorizationHeaderIsInvalid(String authorizationHeader) {
        return authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ");
    }

    private UsernamePasswordAuthenticationToken createToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Usuario userPrincipal = tokenService.parseToken(token);

        if(userPrincipal.getId() == null){
            return null;
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (int i = 0; i < userPrincipal.getFunctions().size(); i++) {
            authorities.add(new SimpleGrantedAuthority(userPrincipal.getFunctions().get(i).getFunction().getNome()));
        }

        if(userPrincipal.isAdmin()){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
       

        return new UsernamePasswordAuthenticationToken(userPrincipal, null, authorities);
    }
    
}
