package com.visitormanagement.security;

public class SecurityConstants {

	public static final String ADMIN_URLS = "/api/v1/admin/**";
	
	//Jwt Token Needs
//	public static final String SECRET_KEY = "SeretKeyToGenerateJwts";
//	public static final String TOKEN_PREFIX = "Bearer ";
//	public static final String HEADER_STRING = "Authorization";
//	public static final long EXPIRATION = 86_400_000;// one day
	
	public static final String SECRET = "SecretKeyToGenJWTs";
	  public static final String TOKEN_PREFIX = "Bearer ";
	  public static final String HEADER_STRING = "Authorization";
	  public static final long EXPIRATION_TIME = 3_600_000; // 
}
