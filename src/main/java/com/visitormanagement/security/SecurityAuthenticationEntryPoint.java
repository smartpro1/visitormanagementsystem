package com.visitormanagement.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.visitormanagement.exceptions.UnauthorizedAccessResponse;

@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		UnauthorizedAccessResponse unauthorizedResponse = new UnauthorizedAccessResponse();
		
		// convert the response to json
		String jsonUnauthorizedResponse = new Gson().toJson(unauthorizedResponse);
		
		response.setContentType("application/json");
		response.setStatus(401);
		response.getWriter().print(jsonUnauthorizedResponse);
	}

}
