package com.visitormanagement.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
//import org.springframework.security.access.prepost.PreAuthorize;
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
import com.visitormanagement.payloads.ChangePasswordRequest;
import com.visitormanagement.payloads.JwtLoginSuccessResponse;
import com.visitormanagement.payloads.PasswordResetRequest;
import com.visitormanagement.security.JwtTokenProvider;
import com.visitormanagement.services.AdminService;
import com.visitormanagement.services.EmailService;
import com.visitormanagement.services.FieldsValidationService;
import com.visitormanagement.validator.AdminValidator;
import com.visitormanagement.validator.ChangePasswordValidator;

import static com.visitormanagement.security.SecurityConstants.TOKEN_PREFIX;
@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	FieldsValidationService validateFields;
	
	@Autowired 
	private AdminValidator adminValidator;
	
	@Autowired
	private ChangePasswordValidator changePasswordValidator;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/register")
	public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRequestPayload adminRequest, BindingResult result){
		// compare passwords
		adminValidator.validate(adminRequest, result);
		
		ResponseEntity<?> errorMap = validateFields.fieldsValidationService(result);
		if(errorMap != null) return errorMap;
		Admin newAdmin = adminService.registerAdmin(adminRequest);
		return new ResponseEntity<Admin>(newAdmin, HttpStatus.CREATED);
	}
	
	
	@PostMapping("/login")
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
	
	
	@PostMapping("/forgot-password")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody PasswordResetRequest passwordResetRequest, BindingResult result,  
			HttpServletRequest httpServletRequest){
		
		ResponseEntity<?> errorMap = validateFields.fieldsValidationService(result);
		if(errorMap != null) return errorMap;
		String adminEmail = passwordResetRequest.getEmail();
		Admin admin = adminService.checkAdminByEmail(adminEmail);
		adminService.generatePasswordResetToken(admin);
		adminService.saveAdmin(admin);
		// something like this : https://mywebapp.com/reset?token=9e5bf4a8-66b8-433e-b91c-6382c1a25f00
		String appUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName();
		String resetToken = admin.getPasswordReset().getResetToken();
		// Email message
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom("smartpromise380@gmail.com");
		passwordResetEmail.setTo(adminEmail);
		passwordResetEmail.setSubject("Password Reset Request");
		passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + ":3000/reset?token=" + resetToken);
		emailService.sendEmail(passwordResetEmail);
			
		return new ResponseEntity<String>("reset password mail sent to " +adminEmail, HttpStatus.OK);		
		
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, BindingResult result){
		// compare password
		changePasswordValidator.validate(changePasswordRequest, result);
		ResponseEntity<?> errorMap = validateFields.fieldsValidationService(result);
		if(errorMap != null) return errorMap;
        adminService.changePassword(changePasswordRequest.getPassword(), changePasswordRequest.getToken());
        
        return new ResponseEntity<String>("password reset successful", HttpStatus.OK);
	}

}
