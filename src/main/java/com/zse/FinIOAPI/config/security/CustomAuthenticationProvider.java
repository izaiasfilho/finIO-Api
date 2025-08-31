package com.zse.FinIOAPI.config.security;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;



/**
 *
 * @author izaias filho
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	//private final  UsuarioSistemaService usuarioSistemaService;
	
	@Value("${credential.username}")
    private String username;

    @Value("${credential.password}")
    private String password;

//	public CustomAuthenticationProvider(UsuarioSistemaService usuarioSistemaService) {
//		this.usuarioSistemaService = usuarioSistemaService;
//	}

	@Override
	public Authentication authenticate(Authentication authentication) {
		Authentication authVerificado = null;

		String mensagemAutenticacao = "";

		try {

			String usuario = String.valueOf(authentication.getPrincipal());

			String senha = String.valueOf(authentication.getCredentials());

			//UsuarioSistema opUsuarioToken = usuarioSistemaService.findByLoginAndSenhaBase64(usuario, senha);
			if (usuario.equals(this.username) && senha.equals(this.password)) {
			    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			    grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			    authVerificado = new UsernamePasswordAuthenticationToken(usuario, senha, grantedAuthorities);
			} else {
			    throw new BadCredentialsException("Usuário ou senha inválidos.");
			}

		} catch (BadCredentialsException e) {
			throw e;
		} catch (Exception e) {
			log.error("Falha na autênticação, dados inválidos. {}", e.getMessage());
			mensagemAutenticacao = "Falha na autênticação, dados inválidos.";
			throw new BadCredentialsException(mensagemAutenticacao);
		}

		return authVerificado;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}
	

}