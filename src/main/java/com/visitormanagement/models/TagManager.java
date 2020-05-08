package com.visitormanagement.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TagManager {
  
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Integer id;
	 private String availableTags;
	 private String tagsInUse;
	 private Integer maxTag;
	 
	 public TagManager() {
		 
	 }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAvailableTags() {
		return availableTags;
	}

	public void setAvailableTags(String availableTags) {
		this.availableTags = availableTags;
	}

	public String getTagsInUse() {
		return tagsInUse;
	}

	public void setTagsInUse(String tagsInUse) {
		this.tagsInUse = tagsInUse;
	}

	public Integer getMaxTag() {
		return maxTag;
	}

	public void setMaxTag(Integer maxTag) {
		this.maxTag = maxTag;
	}
	
	
	
	 
	 
}
