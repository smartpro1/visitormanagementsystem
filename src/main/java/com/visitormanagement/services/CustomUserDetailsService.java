package com.visitormanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.visitormanagement.models.Admin;
import com.visitormanagement.models.UserPrincipal;
import com.visitormanagement.repositories.AdminRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private AdminRepository adminRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		Admin admin = adminRepo.getByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		if(admin == null) throw new UsernameNotFoundException("user not found");
		
		return UserPrincipal.grantedUser(admin);
	}
	
	//@Transactional
	// This method is used by JwtAuthenticationFilter
	public UserDetails loadUserById(Long id) {
		Admin admin = adminRepo.getById(id);
		if(admin == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return UserPrincipal.grantedUser(admin);
	}

}
