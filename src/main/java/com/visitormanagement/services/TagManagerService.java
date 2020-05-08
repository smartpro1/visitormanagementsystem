package com.visitormanagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visitormanagement.exceptions.InvalidTagException;
import com.visitormanagement.models.TagManager;
import com.visitormanagement.repositories.TagManagerRepository;

@Service
public class TagManagerService {

	@Autowired
	private TagManagerRepository tagManagerRepo;
	
	public String generateTag() {
		TagManager tagManager = tagManagerRepo.getById(1);
		String availableTags = tagManager.getAvailableTags();
		
		if(availableTags.length() == 0) {
			int newMaxTag  = tagManager.getMaxTag() + 1;
			tagManager.setMaxTag(newMaxTag);
			String newTagsInUse = tagManager.getTagsInUse() + ", " + newMaxTag;
			tagManager.setTagsInUse(newTagsInUse);
			
			return "TAG" + newMaxTag;
		}
		
		// pick the first tag from the lot
		String visitorTag = availableTags.split(",")[0];
		
		// remove this tag from the available tags
		availableTags = availableTags.replace(visitorTag + ",", "") ;
		
		tagManager.setAvailableTags(availableTags);
		
		String tagsInUse = tagManager.getTagsInUse();
		tagsInUse = tagsInUse + ", " + visitorTag;
		
		tagManager.setTagsInUse(tagsInUse);
		
		return "TAG" + visitorTag;
	}
	

	public void signoutVisitor(String visitorTag) {
		
		TagManager tagManager = tagManagerRepo.getById(1);
		String tagNum = visitorTag.substring(2);
		boolean isTagGenuine = confirmTagExist(tagManager, tagNum);
		if(!isTagGenuine) {
			throw new InvalidTagException("signout declined: invalid tag");
		}
		
		
		String firstEle =  tagManager.getTagsInUse().split(",")[0];
		String newTagsInUse = "";
		
		if(tagManager.getTagsInUse().length() == 3) {
			newTagsInUse = tagManager.getTagsInUse().replace(visitorTag, "");
			
		}else if(firstEle.equals(visitorTag)) {
			newTagsInUse = tagManager.getTagsInUse().replaceFirst( visitorTag + ", ", "");
			
		} else { 
			newTagsInUse = tagManager.getTagsInUse().replaceFirst(", " + visitorTag, "");
		}
		
		tagManager.setTagsInUse(newTagsInUse);
		
		StringBuilder sb = new StringBuilder(tagManager.getAvailableTags());
		sb.append(visitorTag);
		tagManager.setAvailableTags(sb.toString());
		
		tagManagerRepo.save(tagManager);
		}
	
	
	
	public boolean confirmTagExist(TagManager tagManager, String tag) {
		String[] tagsInUseArr = tagManager.getTagsInUse().split(",");
		
		
		for(int i = 0; i < tagsInUseArr.length; i++) {
			if(tagsInUseArr[i].equals(tag)) return true;
		}
		
		return false;
	}
	
}
