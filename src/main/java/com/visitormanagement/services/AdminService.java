package com.visitormanagement.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.visitormanagement.exceptions.EmailAlreadyExistsException;
import com.visitormanagement.exceptions.UsernameAlreadyExistsException;
import com.visitormanagement.exceptions.UsernameOrEmailException;
import com.visitormanagement.models.Admin;
import com.visitormanagement.models.Role;
import com.visitormanagement.models.RoleName;
import com.visitormanagement.payloads.AdminRequestPayload;
import com.visitormanagement.repositories.AdminRepository;
import com.visitormanagement.repositories.RoleRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Admin registerAdmin(AdminRequestPayload adminRequest) {
		checkIfUsernameOrEmailExists(adminRequest);
		
		Admin newAdmin = new Admin(adminRequest.getFullname(), adminRequest.getUsername(), 
				adminRequest.getEmail(), passwordEncoder.encode(adminRequest.getPassword()));
		
		Role adminRole = roleRepo.findByName(RoleName.ROLE_ADMIN);
	     if (adminRole == null) throw new UsernameNotFoundException("role not found");
	     
		newAdmin.setRoles(Collections.singleton(adminRole));
		                
		adminRepo.save(newAdmin);
		return newAdmin;
	}
	
	private void checkIfUsernameOrEmailExists(AdminRequestPayload adminRequest) {
		//Admin admin = adminRepo.getByUsernameOrEmail(adminRequest.getUsername(), adminRequest.getEmail());
		if(adminRepo.existsByUsername(adminRequest.getUsername())) {
			throw new UsernameAlreadyExistsException("username already exists, please choose another.");
		}
		
		if(adminRepo.existsByEmail(adminRequest.getEmail())) {
			throw new EmailAlreadyExistsException("email already in use, please choose another.");
		}
		
	}
}
