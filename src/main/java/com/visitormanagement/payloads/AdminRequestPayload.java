package com.visitormanagement.payloads;

import javax.persistence.Column;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AdminRequestPayload {
	 @NotBlank(message = "fullname is required")
	 @Size(min = 3, message="characters must be more than three letters")
	 private String fullname;
	 @NotBlank(message = "username is required")
	 @Size(min =3, message="username cannot be empty or less than three characters")
	 @Column(updatable = false, unique = true)
	 private String username;
	 @NotBlank(message = "email is required")
	 @Size(min =3, message="email cannot be empty or less than three characters")
	 @Column(updatable = false, unique = true)
	 private String email;
	 @NotBlank(message = "password is required")
	 @Size(min =6, message="password cannot be empty or less than six characters")
	 private String password;
	 
	 @Transient
		private String confirmPassword;
	 
	 
	public AdminRequestPayload() {
		
	}
	
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	 
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	} 
}
