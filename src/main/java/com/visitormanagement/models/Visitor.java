package com.visitormanagement.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
public class Visitor {
	// Visitor profile
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @NotBlank(message = "fullname is required")
	 @Size(min = 3, message="characters must be more than three letters")
	 private String fullname;
	 
	 @NotBlank(message = "phone number is required")
	 @Size(min =11, max=14, message="invalid phone number")
	 @Column(updatable = false, unique = true)
	 private String phone;
	 @NotBlank(message = "address")
	 private String address;
	 @NotBlank(message = "sex is required")
	 @Column(updatable = false)
	 private String sex;
	 @Column(updatable = false)
	 private LocalDateTime created_At;
	 @Column(updatable = false)
	 private String staffOnDuty;
	 
	 // OneToMany with VisitorLog
	 @OneToMany(fetch= FetchType.LAZY, mappedBy="visitor")
	 private List<VisitorLog> visitorLogs = new ArrayList<>();
	 

	public Visitor() {
	}
	
	
	

public Visitor(
			@NotBlank(message = "fullname is required") @Size(min = 3, message = "characters must be more than three letters") String fullname,
			@NotBlank(message = "phone number is required") @Size(min = 11, max = 14, message = "invalid phone number") String phone,
			@NotBlank(message = "address") String address, @NotBlank(message = "sex is required") String sex,
		      String staffOnDuty) {
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
		this.sex = sex;
		this.staffOnDuty = staffOnDuty;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public LocalDateTime getCreated_At() {
		return created_At;
	}

	public void setCreated_At(LocalDateTime created_At) {
		this.created_At = created_At;
	}

	public List<VisitorLog> getVisitorLogs() {
		return visitorLogs;
	}

	public void setVisitorLogs(List<VisitorLog> visitorLogs) {
		this.visitorLogs = visitorLogs;
	}
	 
	@PrePersist
	protected void onCreate(){
		this.created_At = LocalDateTime.now();
	}
	 
	 

}
