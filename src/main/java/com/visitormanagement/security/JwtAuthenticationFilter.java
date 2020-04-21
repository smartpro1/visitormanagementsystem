package com.visitormanagement.security;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.visitormanagement.models.Admin;
import com.visitormanagement.services.CustomUserDetailsService;

import static com.visitormanagement.security.SecurityConstants.HEADER_STRING;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
		String jwt = request.getHeader(HEADER_STRING);
		if(StringUtils.hasText(jwt) && jwtTokenProvider.validateJwtToken(jwt)) {
			Long adminId = jwtTokenProvider.getAdminIdFromJwtToken(jwt);
			Admin adminDetails = customUserDetailsService.loadUserById(adminId);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					adminDetails, null, Collections.emptyList());
			
			// build and set the authentication
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
	} catch(Exception ex) {
		logger.error("could not set user authentication ");
	}
		filterChain.doFilter(request, response);
	}
	
	

}
