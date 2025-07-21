package com.zse.FinIOAPI.config.security;


public class SecurityConstants {

	
	public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";
	
	public static final String AUTH_LOGIN_URL = "/api/login";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	public static final String TOKEN_ISSUER = "secure-api";
	public static final String TOKEN_AUDIENCE = "secure-app";

}
