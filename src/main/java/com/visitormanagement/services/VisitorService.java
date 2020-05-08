package com.visitormanagement.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.visitormanagement.exceptions.InvalidDateException;
import com.visitormanagement.exceptions.InvalidPhoneNumException;
import com.visitormanagement.exceptions.InvalidTagException;
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

	public String registerVisitor(VisitorRequestPayload visitorRequest, String username) {
		
		String visitorTag = generateVisitorTag();
		String signedBy = username;
		
		
		Visitor checkVisitor = visitorRepo.getByPhone(visitorRequest.getPhone());
		if(checkVisitor == null) {
			
			Visitor visitor = initializeVisitor(visitorRequest, signedBy);
			
			VisitorLog visitorLog = initializeVisitorLog(visitorRequest, visitor,  visitorTag, signedBy);
			
			visitorRepo.save(visitor);	
			visitorLogRepo.save(visitorLog);
			
			if(visitorRequest.getAssets() != null) {
				AssetLog assetLog = initializeAssetLog(visitorRequest,  visitorLog);
				assetLogRepo.save(assetLog);
			}
			
			}
		
		if(checkVisitor != null) {
			VisitorLog visitorLog = initializeVisitorLog(visitorRequest, checkVisitor,  visitorTag, signedBy);
			visitorRepo.save(checkVisitor);	
			visitorLogRepo.save(visitorLog);
			
			if(visitorRequest.getAssets() != null) {
				AssetLog assetLog = initializeAssetLog(visitorRequest,  visitorLog);
				assetLogRepo.save(assetLog);
			}
		}
		
		return visitorTag;
	}
	
	
	
	public void signOutVisitor(String visitorTag) {
		VisitorLog visitorLog = visitorLogRepo.getByTag(visitorTag);
		if(visitorLog == null) {
			throw new InvalidTagException("signout declined: invalid visitor tag");
		}
		
		
		
		//visitorLog.setTimeOut(new Date(System.currentTimeMillis()));
		visitorLog.setTimeOut(LocalDateTime.now());
		visitorLogRepo.save(visitorLog);
		
	}
	
	public List<VisitorLog> findAllVisitorsLogByMe(String adminName) {
		List<VisitorLog> myVisitors = visitorLogRepo.findBySignedBy(adminName);
		return myVisitors;
	}
	
	public String generateVisitorTag() {
		 int num = (int)(Math.random() * 1000);
	     String tag = "TAG" + Integer.toString(num);
		return tag;
	}
	
	public Visitor initializeVisitor(VisitorRequestPayload visitorRequest, String adminUsername) {
		Visitor visitor = new Visitor(visitorRequest.getFullname(), visitorRequest.getPhone(),
				visitorRequest.getAddress(), visitorRequest.getSex(),  adminUsername);
		
		return visitor;
	}
	
	public VisitorLog initializeVisitorLog(VisitorRequestPayload visitorRequest, Visitor visitor, 
			                               String visitorTag, String signedBy) {
		VisitorLog visitorLog = new VisitorLog(visitorRequest.getWhomToSee(), visitorRequest.getPurpose(),
                 visitorTag, signedBy, visitor);
		
		return visitorLog;
	}
	
	public AssetLog initializeAssetLog(VisitorRequestPayload visitorRequest, VisitorLog visitorLog) {
		AssetLog assetLog = new AssetLog(visitorRequest.getAssets(), visitorLog);
		
		return assetLog;
	}

	public List<Visitor> findAllVisitorsRegisteredByMe(String adminUsername) {
		List<Visitor> myRegisteredVisitors = visitorRepo.findByStaffOnDuty(adminUsername);
		return myRegisteredVisitors;
	}
	
	public Visitor findVisitorByPhone(String phone) {
		if(phone.length() < 11 || phone.length() > 14) {
			throw new InvalidPhoneNumException("The phone number " + phone + " supplied is invalid.");
		}
		Visitor visitor = visitorRepo.findByPhone(phone);
		return visitor;
	}

// this works

//	public List<VisitorLog> findVisitorsLogsByDateRange(String start, String end) {
//		if(start.length() < 1 || end.length() < 1) {
//			throw new InvalidDateException("Start date or end date cannot be empty");
//		}
//		
//		LocalDate startDate = LocalDate.parse(start);
//		LocalDate endDate = LocalDate.parse(end);
//		if(startDate.isAfter(endDate)) {
//			throw new InvalidDateException("Start date cannot be greater than end date");
//		}
//		
//		List<VisitorLog> visitorsLogs = visitorLogRepo.findByTimeIn(start, end);
//		return visitorsLogs;
//	}



	public Page<VisitorLog> findVisitorsLogsByDateRange(String start, String end, Pageable pageable) {
		if(start.length() < 1 || end.length() < 1) {
			throw new InvalidDateException("Start date or end date cannot be empty");
		}
		
		LocalDate startDate = LocalDate.parse(start);
		LocalDate endDate = LocalDate.parse(end);
		if(startDate.isAfter(endDate)) {
			throw new InvalidDateException("Start date cannot be greater than end date");
		}
		
		Page<VisitorLog> visitorsLogs = visitorLogRepo.findByTimeIn(start, end, pageable);
		return visitorsLogs;
	}



//	public Visitor findVisitorByFullname(String fullname) {
//		Visitor visitor = visitorRepo.findByFullname(fullname);
//		return visitor;
//	}
	
	
}
