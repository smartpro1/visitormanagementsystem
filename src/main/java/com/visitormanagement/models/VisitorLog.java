package com.visitormanagement.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class VisitorLog {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 @NotBlank(message = "you must indicate who you want to see")
	 private String whomToSee;
	 @NotBlank(message = "you must indicate your purpose of visit")
	 private String purpose;
	 @JsonFormat(pattern = "yyyy-mm-dd")
	 @Column(updatable = false)
	 private Date timeIn;
	 private String tag;
	 private String signedBy;
	 @JsonFormat(pattern = "yyyy-mm-dd")
	 @Column(updatable = false)
	 private Date timeOut;
	 
	 // @ManyToOneWith Visitor
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="visitor_id", nullable = false)
	 @JsonIgnore
	 private Visitor visitor;
	 
	 // @OneToOne with AssetLog
	 @OneToOne(fetch = FetchType.LAZY, mappedBy="visitorLog")
	 private AssetLog assetLog;

	public VisitorLog() {
		
	}
	

	public VisitorLog(@NotBlank(message = "you must indicate who you want to see") String whomToSee,
			@NotBlank(message = "you must indicate your purpose of visit") String purpose, Date timeIn, String tag,
			String signedBy, Visitor visitor) {
		this.whomToSee = whomToSee;
		this.purpose = purpose;
		this.timeIn = timeIn;
		this.tag = tag;
		this.signedBy = signedBy;
		this.visitor = visitor;
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getTimeIn() {
		return timeIn;
	}

	public void setTimeIn(Date timeIn) {
		this.timeIn = timeIn;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getSignedBy() {
		return signedBy;
	}

	public void setSignedBy(String signedBy) {
		this.signedBy = signedBy;
	}

	public Date getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(Date timeOut) {
		this.timeOut = timeOut;
	}

	public Visitor getVisitor() {
		return visitor;
	}

	public void setVisitor(Visitor visitor) {
		this.visitor = visitor;
	}
	
	
	
	public AssetLog getAssetLog() {
		return assetLog;
	}

	public void setAssetLog(AssetLog assetLog) {
		this.assetLog = assetLog;
	}

	@PrePersist
	protected void onCreate() {
		this.timeIn = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.timeOut = new Date();
	}
	 
	 
}
