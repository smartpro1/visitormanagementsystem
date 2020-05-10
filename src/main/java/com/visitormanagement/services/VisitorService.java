package com.visitormanagement.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
	private VisitorRepository visitorRepo;
	
	@Autowired
	private VisitorLogRepository visitorLogRepo;
	
	@Autowired
	private AssetLogRepository assetLogRepo;
	
	@Autowired
	private TagManagerService tagManagerService;

	public String registerVisitor(VisitorRequestPayload visitorRequest, String username) {
		
		// check whether visitor already exist
		Visitor checkVisitor = visitorRepo.getByPhone(visitorRequest.getPhone());
		
		// check whether visitor is already signed in
		if(checkVisitor != null) {
			List<VisitorLog> visitorLog = checkVisitor.getVisitorLogs();
			System.out.println(visitorLog);
			VisitorLog visLog = visitorLog.stream().filter(vLog -> vLog.getTimeOut() == null)
					.findFirst().orElse(null);
			System.out.println(visLog);
			if(visLog != null) {
				throw new InvalidTagException("registration declined: this user is currently active.");
			}
		}
		
		//String visitorTag = generateVisitorTag();
		String visitorTag = tagManagerService.generateTag();
		String signedBy = username;
		
		
		
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
	
	
	public List<VisitorLog> findAllVisitorsLogByMe(String adminName) {
		List<VisitorLog> myVisitors = visitorLogRepo.findBySignedBy(adminName);
		return myVisitors;
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


	
	
}
