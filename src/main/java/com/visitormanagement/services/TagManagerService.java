package com.visitormanagement.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visitormanagement.exceptions.InvalidTagException;
import com.visitormanagement.models.TagManager;
import com.visitormanagement.models.VisitorLog;
import com.visitormanagement.repositories.TagManagerRepository;
import com.visitormanagement.repositories.VisitorLogRepository;

@Service
public class TagManagerService {

	@Autowired
	private TagManagerRepository tagManagerRepo;
	
	@Autowired
	private VisitorLogRepository visitorLogRepo;
	
	public String generateTag() {
		TagManager tagManager = tagManagerRepo.getById(1);
		String availableTags = tagManager.getAvailableTags();
		
		if(availableTags.length() == 0 || availableTags == null) {
			int newMaxTag  = tagManager.getMaxTag() + 1;
			tagManager.setMaxTag(newMaxTag);
			String assignedTag = "";
			if(newMaxTag < 10) {
				assignedTag = "00" + newMaxTag;
			} else if(newMaxTag > 9 && newMaxTag < 100) {
				assignedTag = "0" + newMaxTag;
			} else {
				assignedTag = "" +newMaxTag;
			}
			
			String newTagsInUse = tagManager.getTagsInUse();
			if(newTagsInUse.length() == 0) {
				newTagsInUse = assignedTag;
			} else {
				newTagsInUse = newTagsInUse + ", " + assignedTag;
			}
			tagManager.setTagsInUse(newTagsInUse);
			
			return "TAG" + assignedTag;
		}
		
		// pick the first tag from the lot
		String visitorTag = "";
		if(availableTags.length() == 3) {
			visitorTag = availableTags;
			availableTags = "" ;
		} else {
			visitorTag = availableTags.split(",")[0];
			
			// remove this tag from the available tags
			availableTags = availableTags.replace(visitorTag +", " , "") ;
		}
		
		
		tagManager.setAvailableTags(availableTags);
		
		String tagsInUse = tagManager.getTagsInUse();
		if(tagsInUse == null || tagsInUse.length() == 0) {
			tagsInUse = visitorTag;
		} else {
			tagsInUse = tagsInUse + ", " + visitorTag;
		}
		
		
		tagManager.setTagsInUse(tagsInUse);
		
		tagManagerRepo.save(tagManager);
		
		return "TAG" + visitorTag;
	}
	
	


	public void signOutVisitor(String visitorTag) {
		
		if(visitorTag.length() != 6 || !visitorTag.startsWith("TAG")) {
			throw new InvalidTagException("signout declined: invalid tag format.");
		}
		
		VisitorLog visitorLog = visitorLogRepo.getByTag(visitorTag);
		TagManager tagManager = tagManagerRepo.getById(1);
		String tagNum = visitorTag.substring(3);
		
		boolean isTagGenuine = confirmTagExist(tagManager, tagNum);
		if(!isTagGenuine) {
			throw new InvalidTagException("signout declined: invalid tag.");
		}
		
		String firstEle =  tagManager.getTagsInUse().split(",")[0];
		String newTagsInUse = "";
		
		if(tagManager.getTagsInUse().length() == 3) {
			newTagsInUse = tagManager.getTagsInUse().replace(tagNum, "");
			
		}else if(firstEle.equals(tagNum)) {
			newTagsInUse = tagManager.getTagsInUse().replaceFirst( tagNum + ", ", "");
			
		} else { 
			newTagsInUse = tagManager.getTagsInUse().replaceFirst(", " + tagNum, "");
		}
		
		tagManager.setTagsInUse(newTagsInUse);
		
		StringBuilder sb = new StringBuilder(tagManager.getAvailableTags());
		
		if(tagManager.getAvailableTags().length() == 0) {
			sb.append(tagNum);
		} else {
			sb.append(", " + tagNum);
		}
		
		tagManager.setAvailableTags(sb.toString());
		
		tagManagerRepo.save(tagManager);
		
		
		visitorLog.setTimeOut(LocalDateTime.now());
		visitorLogRepo.save(visitorLog);
		
		}
	
	
	
	public boolean confirmTagExist(TagManager tagManager, String tag) {
		String[] tagsInUseArr = tagManager.getTagsInUse().split(", ");
		
		
		for(int i = 0; i < tagsInUseArr.length; i++) {
			if(tagsInUseArr[i].equals(tag)) return true;
		}
		
		return false;
	}
	
}
