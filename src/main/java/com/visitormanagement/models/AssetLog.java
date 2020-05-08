package com.visitormanagement.models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;


import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AssetLog {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 private String assets;
	
	 @Column(updatable = false)
	 private LocalDateTime created_At;
	 
	 // @OneToOne with VisitorLog
	 @OneToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "visitor_logid")
	 @JsonIgnore
	 private VisitorLog visitorLog;
	 
	 public AssetLog() {
		
	}
	 
	 

	public AssetLog(String assets, VisitorLog visitorLog) {
		this.assets = assets;
		this.visitorLog = visitorLog;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}

	public LocalDateTime getCreated_At() {
		return created_At;
	}

	public void setCreated_At(LocalDateTime created_At) {
		this.created_At = created_At;
	}

	public VisitorLog getVisitorLog() {
		return visitorLog;
	}

	public void setVisitorLog(VisitorLog visitorLog) {
		this.visitorLog = visitorLog;
	}
	 
	 @PrePersist
	 protected void onCreate() {
		 this.created_At = LocalDateTime.now();
	 }
	 
	 
}
