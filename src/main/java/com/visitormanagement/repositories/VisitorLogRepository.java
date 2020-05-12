package com.visitormanagement.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.VisitorLog;

@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long>{
    
	@Query(value="SELECT * FROM visitor_log WHERE tag =?1 AND time_out IS NULL", nativeQuery=true)
	VisitorLog getByTag(String visitorTag);
	
	List<VisitorLog> findBySignedBy(String adminName);
    
	// This works
	//@Query(value="SELECT * FROM visitor_log WHERE time_in BETWEEN ?1 AND ?2", nativeQuery=true)
	//List<VisitorLog> findByTimeIn(String start, String end);

	@Query(value="SELECT * FROM visitor_log WHERE time_in BETWEEN ?1 AND ?2", nativeQuery=true)
	Page<VisitorLog> findByTimeIn(String start, String end, Pageable pageable);

	@Query(value = "SELECT * FROM visitor_log WHERE time_in > ?1", nativeQuery = true)
	List<VisitorLog> findLogsToday(LocalDateTime midNightYesterday);

	
	
	
	
	
//	@Query(value = "SELECT p FROM VisitorLog p WHERE p.tag =?1 AND p.timeOut is null")
//	VisitorLog findByTag(String visitorTag);
	// JPQL
	//@Query(value = "SELECT p FROM Visitor p WHERE p.fullname =?1")
	//Visitor findByFullname(String fullname);
	
	


}
