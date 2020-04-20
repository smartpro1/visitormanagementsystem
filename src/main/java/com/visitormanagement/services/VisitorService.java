package com.visitormanagement.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visitormanagement.models.AssetLog;
import com.visitormanagement.models.Visitor;
import com.visitormanagement.models.VisitorLog;
import com.visitormanagement.payloads.VisitorRequestPayload;
import com.visitormanagement.repositories.AssetLogRepository;
import com.visitormanagement.repositories.VisitorLogRepository;
import com.visitormanagement.repositories.VisitorRepository;

@Service
public class VisitorService {
	
	@Autowired
	VisitorRepository visitorRepo;
	
	@Autowired
	VisitorLogRepository visitorLogRepo;
	
	@Autowired
	AssetLogRepository assetLogRepo;

	public String registerVisitor(VisitorRequestPayload visitorRequest) {
		
		String visitorTag = generateVisitorTag();
		String signedBy = "Admin";
		Date createdAt = new Date();
		
		Visitor checkVisitor = visitorRepo.getByPhone(visitorRequest.getPhone());
		if(checkVisitor == null) {
			
			Visitor visitor = initializeVisitor(visitorRequest);
			
			VisitorLog visitorLog = initializeVisitorLog(visitorRequest, visitor, createdAt, visitorTag, signedBy);
			
			visitorRepo.save(visitor);	
			visitorLogRepo.save(visitorLog);
			
			if(visitorRequest.getAssets() != null) {
				AssetLog assetLog = initializeAssetLog(visitorRequest, createdAt, visitorLog);
				assetLogRepo.save(assetLog);
			}
			
			}
		
		if(checkVisitor != null) {
			VisitorLog visitorLog = initializeVisitorLog(visitorRequest, checkVisitor, createdAt, visitorTag, signedBy);
			visitorRepo.save(checkVisitor);	
			visitorLogRepo.save(visitorLog);
			
			if(visitorRequest.getAssets() != null) {
				AssetLog assetLog = initializeAssetLog(visitorRequest, createdAt, visitorLog);
				assetLogRepo.save(assetLog);
			}
		}
		
		return visitorTag;
	}
	
	public String generateVisitorTag() {
		 int num = (int)(Math.random() * 1000);
	     String tag = "tag" + Integer.toString(num);
		return tag;
	}
	
	public Visitor initializeVisitor(VisitorRequestPayload visitorRequest) {
		Visitor visitor = new Visitor(visitorRequest.getFullname(), visitorRequest.getPhone(),
				visitorRequest.getAddress(), visitorRequest.getSex(), new Date());
		
		return visitor;
	}
	
	public VisitorLog initializeVisitorLog(VisitorRequestPayload visitorRequest, Visitor visitor, Date createdAt, 
			                               String visitorTag, String signedBy) {
		VisitorLog visitorLog = new VisitorLog(visitorRequest.getWhomToSee(), visitorRequest.getPurpose(),
                createdAt, visitorTag, signedBy, visitor);
		
		return visitorLog;
	}
	
	public AssetLog initializeAssetLog(VisitorRequestPayload visitorRequest, Date createdAt, VisitorLog visitorLog) {
		AssetLog assetLog = new AssetLog(visitorRequest.getAssets(), createdAt, visitorLog);
		
		return assetLog;
	}
}
