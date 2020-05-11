package com.visitormanagement.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class PasswordReset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String resetToken;
	private long expiryDate;
	
	// OneToOne with Admin
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name="user_id")
	@JsonIgnore
	private Admin admin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}
}
