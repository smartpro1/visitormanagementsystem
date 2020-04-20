package com.visitormanagement.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.visitormanagement.security.SecurityConstants.ADMIN_URLS;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.cors().and().csrf().disable()
		    .authorizeRequests()
		    .antMatchers(
		    		  "/",
		    		     "/**/*.png",
		    		     "/**/*.gif",
		    		     "/**/*.svg",
		    		     "/**/*.jpg",
		    		     "/**/*.html",
		    		     "/**/*.css",
		    		     "/**/*.js"
		    		).permitAll()
		    .antMatchers(ADMIN_URLS).permitAll()
		    .anyRequest()
		    .authenticated();
		    
	}

}
