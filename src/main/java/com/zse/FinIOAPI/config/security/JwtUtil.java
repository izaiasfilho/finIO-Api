package com.zse.FinIOAPI.config.security;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

	private final HttpServletRequest httpServletRequest;

	@Value("${posmei.jwt.secret}")
	public String secret;// = "9UBy;Fj|6_mtM`K):mzx|5TVHKreg0DwB2%g4%yC[-+T^ '2G H?g*q;}M^$-7MGjmH@O7IJDjo~^ENUBg]5(3A=l[6xg_7O/o{it?l/!PFeOi5qHSfR8>so";

	@Value("${posmei.jwt.expiration}")
	public Long expiration;// = 86400000L;

	public JwtUtil(HttpServletRequest httpServletRequest) {
		this.httpServletRequest = httpServletRequest;
	}

	public Map<String, String> decodeToken(String token) {
		Map<String, String> map = new HashMap<String, String>();

		if (StringUtils.isEmpty(token))
			return map;

		String hashTokenValue = removeTokenPrefix(token);
		String[] parts = hashTokenValue.split("\\."); 

		map.put("Headers", new String(Base64.decodeBase64(parts[0]))); // Header
		map.put("Payload", new String(Base64.decodeBase64(parts[1]))); // Payload

		return map;
	}

	public String removeTokenPrefix(String token) {
		if (StringUtils.isEmpty(token))
			return token;
		return token.replace(SecurityConstants.TOKEN_PREFIX, "");
	}
	
	

	public String generateToken(User userdetails) {
		var roles = userdetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		var signingKey = secret.getBytes();
		Date newDateExpiration = new Date(System.currentTimeMillis() + expiration);
		String newToken = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE).setIssuer(SecurityConstants.TOKEN_ISSUER)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE).setSubject(userdetails.getUsername())
				.setExpiration(newDateExpiration).claim("rol", roles).compact();

		return newToken;
	}
	
	  public String generateToken(String username, String password) {
		  
		  User userDetails = new User(username, password, new ArrayList<>());
		  return generateToken(userDetails);
	    }

	public String generateToken(Authentication authentication) {
		User userPrincipal;

		if (authentication.getPrincipal() instanceof UserDetails)
			userPrincipal = (User) authentication.getPrincipal();
		else
			userPrincipal = new User(String.valueOf(authentication.getPrincipal()),
					String.valueOf(authentication.getCredentials()),
					authentication.getAuthorities());
		return this.generateToken(userPrincipal);
	}

	public boolean tokenValido(String token) {
		if (StringUtils.isEmpty(token))
			return false;

		String hashTokenValue = removeTokenPrefix(token);
		Claims claims = getClaims(hashTokenValue);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}

	public String getUsername() {
		String hashTokenValue = this.getHashTokenValue(httpServletRequest);
		if (StringUtils.isEmpty(hashTokenValue)) {
			return "";
		}
		return getUsername(hashTokenValue);
	}

	public String getCpfCnpjUser() {
		String userName = this.getUsername();
		if (userName == null)
			return null;
		String cpfCnpj = new String(Base64.decodeBase64(userName));
		return cpfCnpj;
	}

	public List<SimpleGrantedAuthority> getGrantedAuthority(String token) {
		Claims claims = getClaims(token);
		List<SimpleGrantedAuthority> authorities = ((List<?>) claims.get("rol")).stream()
				.map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());
		return authorities;
	}

	private Claims getClaims(String token) {
		if (StringUtils.isEmpty(token))
			return null;

		String hashTokenValue = removeTokenPrefix(token);

		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(hashTokenValue).getBody();
		} catch (ExpiredJwtException exception) {
			log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
			return exception.getClaims();
		} catch (UnsupportedJwtException exception) {
			log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
			return null;
		} catch (MalformedJwtException exception) {
			log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
			return null;
		} catch (IllegalArgumentException exception) {
			log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
			return null;
		} catch (Exception exception) {
			log.warn("Request to parse corruped: {} failed : {}", hashTokenValue, exception.getMessage());
			return null;
		}
	}

	public String getHashTokenValue(HttpServletRequest request) {
		String hashTokenValue = "";

		if (request != null) {
			try {
				var token = request.getHeader(SecurityConstants.TOKEN_HEADER);
				if (!StringUtils.isEmpty(token))
					hashTokenValue = token.replace(SecurityConstants.TOKEN_PREFIX, "");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return hashTokenValue;
	}

	public String getTokenRefreshValidate(String tokenOld) {
		Claims claims = getClaims(tokenOld);
		if (claims != null) {
			var authentication = getUserAuthenticationToken(tokenOld);
			var user = new User(String.valueOf(authentication.getPrincipal()),
					String.valueOf(authentication.getCredentials()), authentication.getAuthorities());
			return SecurityConstants.TOKEN_PREFIX + generateToken(user);
		}
		return null;
	}

	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (!StringUtils.isEmpty(token)) {
			if (tokenValido(token)) {
				var userAuthenticationToken = getUserAuthenticationToken(token);
				return userAuthenticationToken;
			}
		}
		return null;
	}

	public UsernamePasswordAuthenticationToken getUserAuthenticationToken(String token) {
		if (!StringUtils.isEmpty(token)) {
			var username = getUsername(token);
			var authorities = getGrantedAuthority(token);

			return new UsernamePasswordAuthenticationToken(username, null, authorities);
		}
		return null;
	}

	public boolean isUserAuthentication(Authentication authentication) {
		return (!(authentication instanceof AnonymousAuthenticationToken));
	}
}
