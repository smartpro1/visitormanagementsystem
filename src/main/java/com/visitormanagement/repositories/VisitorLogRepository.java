package com.visitormanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.VisitorLog;

@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long>{

	VisitorLog getByTag(String visitorTag);

	List<VisitorLog> findBySignedBy(String adminName);

}
