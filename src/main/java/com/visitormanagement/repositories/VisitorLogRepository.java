package com.visitormanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.VisitorLog;

@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long>{

	VisitorLog getByTag(String visitorTag);

	List<VisitorLog> findBySignedBy(String adminName);
    
	@Query(value="SELECT * FROM visitor_log WHERE time_in BETWEEN ?1 AND ?2", nativeQuery=true)
	List<VisitorLog> findByTimeIn(String start, String end);
	
	
	// JPQL
	//@Query(value = "SELECT p FROM Visitor p WHERE p.fullname =?1")
	//Visitor findByFullname(String fullname);

	// SQL
	//@Query(value="SELECT * FROM visitor where fullname = ?1", nativeQuery=true)
	//Visitor findByFullname(String fullname);
	//com.visitormanagement.services.Visitor findByPhone(int phone);

}
