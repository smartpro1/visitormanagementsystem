package com.visitormanagement.payloads;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VisitorRequestPayload {
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
	 private String sex;
	 @NotBlank(message = "whom to see is required")
	 private String whomToSee;
	 @NotBlank(message = "purpose is required")
	 private String purpose;
	 private String assets;
	 
	 public VisitorRequestPayload() {
		 
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

	public String getWhomToSee() {
		return whomToSee;
	}

	public void setWhomToSee(String whomToSee) {
		this.whomToSee = whomToSee;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}
	
	
	 
	 
}
