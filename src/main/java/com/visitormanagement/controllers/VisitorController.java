package com.visitormanagement.controllers;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visitormanagement.models.Visitor;
import com.visitormanagement.models.VisitorLog;
import com.visitormanagement.payloads.DateRangeRequest;
import com.visitormanagement.payloads.VisitorRequestPayload;
import com.visitormanagement.services.FieldsValidationService;
import com.visitormanagement.services.TagManagerService;
import com.visitormanagement.services.VisitorService;

@RestController
@RequestMapping("/api/v1/visitors")
@CrossOrigin
public class VisitorController {
	
	@Autowired
	private FieldsValidationService validateFields;
	
	@Autowired 
	private VisitorService visitorService;
	
	@Autowired
	private TagManagerService tagManagerService;
	
	@GetMapping("/registered")
	public ResponseEntity<List<Visitor>> getAllVisitorsRegisteredByMe(Principal principal){
		List<Visitor> myRegisteredVisitors = visitorService.findAllVisitorsRegisteredByMe(principal.getName());
		return new ResponseEntity<List<Visitor>>(myRegisteredVisitors, HttpStatus.OK);
	}
	
    @GetMapping("/logs")
    public ResponseEntity<List<VisitorLog>> getAllVisitorsLogByMe(Principal principal){
        List<VisitorLog> myVisitorsLogs = visitorService.findAllVisitorsLogByMe(principal.getName());
        return new ResponseEntity<List<VisitorLog>>(myVisitorsLogs, HttpStatus.OK);
    }
    
    @GetMapping("/{phone}")
    public ResponseEntity<?> getVisitorByPhoneNum(@PathVariable String phone){
    	Visitor visitor = visitorService.findVisitorByPhone(phone);
    	
    	if(visitor == null) {
    		return new ResponseEntity<String>("No result found", HttpStatus.OK); 
    	}
    	return new ResponseEntity<Visitor>(visitor, HttpStatus.OK); 
    }
    
    @GetMapping("/todays-logs")
	public List<VisitorLog> getLogsForToday(){
		List<VisitorLog> visitorsLogsToday = visitorService.getVisitorLogsForToday();
		
		if(visitorsLogsToday == null) return null;
		
		return visitorsLogsToday;
	}
    

    
    @PostMapping("/track-visitors")
    public Page<VisitorLog> getVisitorsLogsByDateRange(@RequestBody DateRangeRequest dateRangeRequest, Pageable pageable){
    	Page<VisitorLog> dateRangeVisitors = visitorService.findVisitorsLogsByDateRange(dateRangeRequest.getStart(), 
    			                           dateRangeRequest.getEnd(), pageable);
    	return dateRangeVisitors;
    }

	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
	@PostMapping("register-visitor")
	public ResponseEntity<?> registerVisitor(@Valid @RequestBody VisitorRequestPayload visitorRequest, BindingResult result, Principal principal){
		ResponseEntity<?> errorMap = validateFields.fieldsValidationService(result);
		if(errorMap != null) return errorMap;
		
		String tag = visitorService.registerVisitor(visitorRequest, principal.getName());

		return new ResponseEntity<String>("Visitor's tag is " + tag, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERADMIN')")
	@PostMapping("logout/{visitorTag}")
	public ResponseEntity<?> signOutVisitor(@PathVariable String visitorTag){
		//visitorService.signOutVisitor(visitorTag);
		tagManagerService.signOutVisitor(visitorTag);
			return new ResponseEntity<String>("visitor successfully logged out", HttpStatus.OK);
			
	}
	

}
