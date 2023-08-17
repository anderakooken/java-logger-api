package com.maissabor.services.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.maissabor.services.dao.Repository.UsuarioRepository;
import com.maissabor.services.models.entitys.Usuario;
import com.maissabor.services.models.entitys.requests.AuthRequest;
import com.maissabor.services.models.entitys.response.AuthResponse;
import com.maissabor.services.models.entitys.response.ErrorResponse;
import com.maissabor.services.models.entitys.token.JWTTokenService;
import io.jsonwebtoken.security.InvalidKeyException;

@RestController
public class AuthApi {
    
    @Autowired
    private UsuarioRepository userDao;

    private static final Logger log = LoggerFactory.getLogger(AuthApi.class);

    // @Autowired
    // private PermitionsRepository permitDao;

    private JWTTokenService tokenService;

    private final String LOCALHOST_IPV4 = "127.0.0.1";
	private final String LOCALHOST_IPV6 = "0:0:0:0:0:0:0:1";

    @Autowired
    private HttpServletRequest request;

    public AuthApi(JWTTokenService tokenService){
        this.tokenService = tokenService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request, @RequestHeader(value = "User-Agent") String userAgent){
        // System.out.println(getClientIp(this.request));
        String clientIp = getClientIp(this.request);
        System.out.println(this.request.getHeader("User-Agent"));
        Optional<Usuario> optUser = userDao.findByEmail(request.getEmail());

        if(optUser.isPresent()){

            Usuario user = optUser.get();
            try{
                StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
             
                String pwDecripted = "";

                if(user.getPw() != null && !user.getPw().isEmpty()){
                    pwDecripted = user.getPw();
                }

            
                
                if(user.getPw() != null && !user.getPw().isEmpty() && passwordEncryptor.checkPassword(request.getPassword(), pwDecripted)){
 
                    return ResponseEntity.ok(new AuthResponse(user.getEmail(), clientIp, tokenService.generateToken(user), false));

                }else if(request.getPassword().equals(request.getEmail()) && (user.getPw() == null || user.getPw().isEmpty())){

                    return ResponseEntity.ok(new AuthResponse(user.getEmail(), clientIp, tokenService.generateToken(user), true));

                }
                
            }catch(InvalidKeyException e){
                log.warn("Erro a decriptar senha do banco! :: login()");
            }

            String[] list = {"Usuário ou senha incorretos"};
            return new ResponseEntity<>(new ErrorResponse(404, "Usuário ou senha incorretos!", "", Arrays.asList(list)), HttpStatus.NOT_FOUND);
            
        }else{
            String[] list = {"Usuário ou senha incorretos"};
            return new ResponseEntity<>(new ErrorResponse(404, "Usuário ou senha incorretos!", "", Arrays.asList(list)), HttpStatus.NOT_FOUND);
        }
        
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PostMapping("/auth/verify")
    public ResponseEntity<?> verifyToken(@RequestBody HashMap<String,String> json){

        if(json.get("token") == null || json.get("token").isEmpty() || json.get("token").isBlank()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Usuario user = this.tokenService.parseToken(json.get("token").replace("Bearer ", ""));
        if(user.getEmail() != null || user.getEmail().isEmpty()){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/auth/changePW")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid AuthRequest auth){
        Optional<Usuario> optUser = userDao.findByEmail(auth.getEmail());
        
        if(optUser.isPresent()){
            
            Usuario user = optUser.get();
            StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
            
            user.setPw(passwordEncryptor.encryptPassword(auth.getPassword()));

            return ResponseEntity.ok(userDao.save(user));
            
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    // }

    /**
     * Método para tratamento de IP do cliente.
     * @param request
     * @return
     */
    public String getClientIp(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-Forwarded-For");
		if(ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		
		if(ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		
		if(ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if(LOCALHOST_IPV4.equals(ipAddress) || LOCALHOST_IPV6.equals(ipAddress)) {
				try {
					InetAddress inetAddress = InetAddress.getLocalHost();
					ipAddress = inetAddress.getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(ipAddress != null && !ipAddress.isEmpty()
				&& ipAddress.length() > 15
				&& ipAddress.indexOf(",") > 0) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
		}
		
		return ipAddress;
	}
}
