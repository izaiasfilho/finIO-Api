package com.zse.FinIOAPI.controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zse.FinIOAPI.config.security.JwtUtil;
import com.zse.FinIOAPI.entity.UsuarioCredenciais;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@Tag(name = "Authorization")
@RequestMapping("/api")
public class AuthController {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Operation(summary = "Login do Sistema",
	           responses = {
			      @ApiResponse(responseCode = "200",  
			    		      description = "Sucesso no login para geração do token de autenticação",
			    		      content = @Content(examples = {@ExampleObject(name = "token", 
			    		      												summary = "Login do Sistema", 
			    		      												description = "Login para geração do token de autenticação",
			    		      												 value = "{\"username\": \"RNiPd2Kap4SAP\", \"password\":\"2d7GfGjuk6ZCpEXonFjOWFxIcYZrrZDR\"}")}, 
			    		                         mediaType = MediaType.APPLICATION_JSON_VALUE),			    		      
			    		  	  headers = {@Header(name = "Authorization", schema = @Schema(implementation = String.class), description = "[Token de Autorização] - Criptografia 'HS512', tipo 'JWT'"),
			    		  			     @Header(name = "Access-Control-Expose-Headers", schema = @Schema(implementation = String.class), description = "Palavra chave 'Authorization'")}
			      ),			      
				  @ApiResponse(responseCode = "401", description = "Falha na autênticação, login ou senha inválida.", 
				  				//content = @Content(schema = @Schema(implementation = ErrorCredenciais.class))
				  				content = @Content(examples = {@ExampleObject(value = "{\n"
				  						+ "  \"timestamp\": 1707424086852,\n"
				  						+ "  \"status\": 401,\n"
				  						+ "  \"error\": \"Nao autorizado\",\n"
				  						+ "  \"message\": \"UsuarioSistema ou senha invalidos\",\n"
				  						+ "  \"path\": \"/api/authenticate\"\n"
				  						+ "}")})
						  		),
				  @ApiResponse(responseCode = "403", description = "Não foi possível realizar a autênticação, contacte a equipe técnica.")
	})	
	@PostMapping("login")
    public ResponseEntity<String> login(@Parameter(example = "{\"username\": \"RNiPd2Kap4SAP\", \"password\":\"2d7GfGjuk6ZCpEXonFjOWFxIcYZrrZDR\"}") 
                                        @RequestBody UsuarioCredenciais credenciais) {
        try {
            String token = jwtUtil.generateToken(credenciais.getUsername(), credenciais.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Falha na autenticação, login ou senha inválida.");
        }
    }	

	
	@PostMapping("auth/refresh_token")
	public ResponseEntity<Void> refreshToken(@Parameter(hidden = true) Authentication authentication, 
			@Parameter(hidden = true) HttpServletResponse response) throws IOException {
		
		if (jwtUtil.isUserAuthentication(authentication)) {
			String token = jwtUtil.generateToken(authentication);
			response.addHeader("Authorization", "Bearer " + token);
			response.addHeader("access-control-expose-headers", "Authorization");
			return ResponseEntity.ok().build();			
		} else {
			throw new ResponseStatusException(
			        HttpStatus.UNAUTHORIZED,
			        "Nenhum usuário autenticado para validação do token!");
	}
	}
	
}
