package com.visitormanagement.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.visitormanagement.models.Admin;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static com.visitormanagement.security.SecurityConstants.EXPIRATION;
import static com.visitormanagement.security.SecurityConstants.SECRET_KEY;

@Component
public class JwtTokenProvider {
	
	public String generateJwtToken(Authentication authentication) {
		
		Admin admin = (Admin) authentication.getPrincipal();
		String adminId = Long.toString(admin.getId());
		
		Date now = new Date(System.currentTimeMillis());
		Date expiryDate = new Date(now.getTime() + EXPIRATION);
		
	    Map<String, Object> claims = new HashMap<>();
	    claims.put("id", adminId);
	    claims.put("username", admin.getUsername());
	    claims.put("email", admin.getEmail());
	    claims.put("fullname", admin.getFullname());
		
		return Jwts.builder()
				.setSubject(adminId)
				.setClaims(claims)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY)
				.compact();
		
	}
	
	// validate jwtToken
	public boolean validateJwtToken(String token) {
		try {
			Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			System.out.println("Invalid JWT Signature");
		}catch (MalformedJwtException  ex) {
			System.out.println("Invalid JWT Token");
		}catch (ExpiredJwtException ex) {
			System.out.println("Expired JWT Token");
		}catch (UnsupportedJwtException ex) {
			System.out.println("Unsupported JWT Token");
		}catch (IllegalArgumentException ex) {
			System.out.println("JWT claims string is empty");
		}
		return false;
	}
	
	public Long getAdminIdFromJwtToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		String adminId = (String)claims.get("id");
		 return Long.parseLong(adminId);
		 
		 // would test this later
//		Long adminId = Long.parseLong(claims.getId());
//		return adminId;
	}

}


