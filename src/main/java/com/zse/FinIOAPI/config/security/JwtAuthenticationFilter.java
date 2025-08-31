package com.zse.FinIOAPI.config.security;


import java.io.IOException;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
//import com.tec4z.posmei.entity.UsuarioCredenciais;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
																				// validar o token
	private static final long EXPIRATION_TIME = 7200000; // 2 hours in milliseconds


	
	private static final String SECRET = "9UBy;Fj|6_mtM`K):mzx|5TVHKreg0DwB2%g4%yC[-+T^ '2G H?g*q;}M^$-7MGjmH@O7IJDjo~^ENUBg]5(3A=l[6xg_7O/o{it?l/!PFeOi5qHSfR8>so";
    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

	private final AuthenticationManager authenticationManager;


	private JwtUtil jwtUtil;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);

		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;

	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		Map<String, String> credentials = new HashMap<>();
		try {
		    credentials = new ObjectMapper().readValue(
		        request.getInputStream(),
		        new com.fasterxml.jackson.core.type.TypeReference<Map<String, String>>() {}
		    );
		} catch (IOException e) {
		    log.error("Erro ao tentar autenticar.", e);
		    throw new RuntimeException("Falha ao ler as credenciais da requisição", e);
		}

		String username = credentials.get("username");
		String password = credentials.get("password");

		var authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

		return authenticationManager.authenticate(authenticationToken);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain, Authentication authentication) {

		var user = new User(String.valueOf(authentication.getPrincipal()),
				String.valueOf(authentication.getCredentials()), authentication.getAuthorities());
		var token = jwtUtil.generateToken(user);

		response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX + token);
		response.addHeader("access-control-expose-headers", "Authorization");
	}


	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Usuário ou senha inválidos\", " + "\"path\": \"/api/authenticate\"}";
		}
	}

	public static String createTokenResetPassword(Long idUser, String userName) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

		log.info("Creating token. Issued at: " + now + ", Expiration time: " + expiryDate);

		return Jwts.builder()
				.setSubject(idUser + " - " + userName)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(key)
				.compact();
	}

	
	public static String[] getUserInfoFromJWT(String token) {
		String subject = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();

		String[] userInfo = subject.split(" - ");

		if (userInfo.length != 2) {
			throw new IllegalArgumentException("Formato de subject inválido no token JWT.");
		}

		return userInfo;
	}

	
	public static boolean validateToken(String token) {
		try {
			Date now = new Date();
			Date expiration = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody()
					.getExpiration();


			return !now.after(expiration);
		} catch (Exception e) {
			return false;
		}
	}
}