package com.visitormanagement.services;

import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.visitormanagement.exceptions.EmailAlreadyExistsException;
import com.visitormanagement.exceptions.InvalidTokenException;
import com.visitormanagement.exceptions.UsernameAlreadyExistsException;
import com.visitormanagement.exceptions.UsernameOrEmailException;
import com.visitormanagement.models.Admin;
import com.visitormanagement.models.PasswordReset;
import com.visitormanagement.models.Role;
import com.visitormanagement.models.RoleName;
import com.visitormanagement.payloads.AdminRequestPayload;
import com.visitormanagement.repositories.AdminRepository;
import com.visitormanagement.repositories.PasswordResetRepository;
import com.visitormanagement.repositories.RoleRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepo;
	
	@Autowired
	private PasswordResetRepository passwordResetRepo;
	
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
		  
		 PasswordReset passwordReset = new PasswordReset();
		 newAdmin.setPasswordReset(passwordReset);
		 passwordReset.setAdmin(newAdmin);
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
	
	public void saveAdmin(Admin admin) {
		 adminRepo.save(admin);
	}

	public Admin checkAdminByEmail(String adminEmail) {
		Admin checkAdmin = adminRepo.findByEmail(adminEmail);
		if(checkAdmin == null) {
			throw new UsernameOrEmailException("it appears you do not have an account with us");
		}
		return checkAdmin;
	}

	public PasswordReset generatePasswordResetToken(Admin admin) {
		PasswordReset passwordReset = admin.getPasswordReset();
		System.out.println(passwordReset);
		long validityTime = 60 * 60 * 24 * 1000; // 1 day
		Date now = new Date();
		passwordReset.setExpiryDate(now.getTime() + validityTime);
		String passResetToken = UUID.randomUUID().toString();
		passwordReset.setResetToken(passResetToken);
		passwordResetRepo.save(passwordReset);
		return passwordReset;
		
	}
	
	public void changePassword(String password, String token) {
		PasswordReset passwordReset = passwordResetRepo.findByResetToken(token);
		Admin admin = adminRepo.getById(passwordReset.getAdmin().getId());
		long timeNow = new Date().getTime();
		long checkExpiration = passwordReset.getExpiryDate() - timeNow;
		if(passwordReset == null || checkExpiration < 1 || admin == null) {
			throw new InvalidTokenException("invalid token or expired token");
		}
		admin.setPassword(passwordEncoder.encode(password));
		saveAdmin(admin);
		passwordReset.setResetToken(null);
		passwordResetRepo.delete(passwordReset);
		//passwordResetRepo.deleteById(passwordReset.getId());	
	}
}
