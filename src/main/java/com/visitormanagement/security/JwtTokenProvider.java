package com.visitormanagement.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.visitormanagement.models.UserPrincipal;

import io.jsonwebtoken.*;


import static com.visitormanagement.security.SecurityConstants.EXPIRATION_TIME;
import static com.visitormanagement.security.SecurityConstants.SECRET;

@Component
public class JwtTokenProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	public String generateJwtToken(Authentication authentication) {
		
		UserPrincipal admin = (UserPrincipal) authentication.getPrincipal();
		
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);
		
		String adminId = Long.toString(admin.getId());
	    Map<String, Object> claims = new HashMap<>();
	    claims.put("id", adminId);
	    claims.put("username", admin.getUsername());
	    claims.put("fullname", admin.getFullname());
		
		String jws = Jwts.builder()
				.setSubject(adminId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		return jws;
		
	}
	
	// validate jwtToken
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT Signature: " + ex);
		}catch (MalformedJwtException  ex) {
			logger.error("Malformed JWT Token: " + ex);
		}catch (ExpiredJwtException ex) {
			logger.error("Expired JWT Token: " + ex);
		}catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT Token: " + ex);
		}catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty: " + ex);
		}
		return false;
	}
	
	public Long getAdminIdFromJwtToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
		String adminId = (String)claims.get("id");
		 return Long.parseLong(adminId);

	}

}


