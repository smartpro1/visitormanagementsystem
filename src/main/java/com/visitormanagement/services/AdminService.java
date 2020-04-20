package com.visitormanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.visitormanagement.exceptions.UsernameOrEmailException;
import com.visitormanagement.models.Admin;
import com.visitormanagement.payloads.AdminRequestPayload;
import com.visitormanagement.repositories.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Admin registerAdmin(AdminRequestPayload adminRequest) {
		boolean checkAdminExistence = checkIfUsernameOrEmailExists(adminRequest);
		if(checkAdminExistence) {
			throw new UsernameOrEmailException("Registration declined: Username or Email already taken");
		}
		
		Admin newAdmin = new Admin(adminRequest.getFullname(), adminRequest.getUsername(), 
				adminRequest.getEmail(), passwordEncoder.encode(adminRequest.getPassword()));
		adminRepo.save(newAdmin);
		return newAdmin;
	}
	
	private boolean checkIfUsernameOrEmailExists(AdminRequestPayload adminRequest) {
		Admin admin = adminRepo.getByUsernameOrEmail(adminRequest.getUsername(), adminRequest.getPassword());
		if(admin == null) return false;
		return true;
	}
}
