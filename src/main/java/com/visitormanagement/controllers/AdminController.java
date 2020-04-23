package com.visitormanagement.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visitormanagement.models.Admin;
import com.visitormanagement.payloads.AdminLoginPayload;
import com.visitormanagement.payloads.AdminRequestPayload;
import com.visitormanagement.payloads.JwtLoginSuccessResponse;
import com.visitormanagement.security.JwtTokenProvider;
import com.visitormanagement.services.AdminService;
import com.visitormanagement.services.FieldsValidationService;
import com.visitormanagement.validator.AdminValidator;

import static com.visitormanagement.security.SecurityConstants.TOKEN_PREFIX;
@RestController
@RequestMapping("/api/v1/admin/")
@CrossOrigin
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	FieldsValidationService validateFields;
	
	@Autowired 
	private AdminValidator adminValidator;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@PostMapping("register")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRequestPayload adminRequest, BindingResult result){
		// compare passwords
		adminValidator.validate(adminRequest, result);
		
		ResponseEntity<?> errorMap = validateFields.fieldsValidationService(result);
		if(errorMap != null) return errorMap;
		Admin newAdmin = adminService.registerAdmin(adminRequest);
		return new ResponseEntity<Admin>(newAdmin, HttpStatus.CREATED);
	}
	
	
	@PostMapping("login")
	public ResponseEntity<?> loginAdmin(@Valid @RequestBody AdminLoginPayload adminLoginRequest, BindingResult result) {
		ResponseEntity<?> errorMap = validateFields.fieldsValidationService(result);
		if(errorMap != null) return errorMap;
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						adminLoginRequest.getUsernameOrEmail(), adminLoginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = TOKEN_PREFIX + jwtTokenProvider.generateJwtToken(authentication);
		return ResponseEntity.ok(new JwtLoginSuccessResponse(true, jwt));
	}

}
