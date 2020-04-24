package com.visitormanagement.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
		boolean checkAdminExistence = checkIfUsernameOrEmailExists(adminRequest);
		if(checkAdminExistence) {
			throw new UsernameOrEmailException("Registration declined: Username or Email already taken");
		}
		
		Admin newAdmin = new Admin(adminRequest.getFullname(), adminRequest.getUsername(), 
				adminRequest.getEmail(), passwordEncoder.encode(adminRequest.getPassword()));
		
		Role adminRole = roleRepo.findByName(RoleName.ROLE_ADMIN);
	     if (adminRole == null) throw new UsernameNotFoundException("role not found");
	     
		newAdmin.setRoles(Collections.singleton(adminRole));
		                
		adminRepo.save(newAdmin);
		return newAdmin;
	}
	
	private boolean checkIfUsernameOrEmailExists(AdminRequestPayload adminRequest) {
		Admin admin = adminRepo.getByUsernameOrEmail(adminRequest.getUsername(), adminRequest.getEmail());
		if(admin == null) return false;
		return true;
	}
}
