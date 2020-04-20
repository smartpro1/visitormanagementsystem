package com.visitormanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.VisitorLog;

@Repository
public interface VisitorLogRepository extends JpaRepository<VisitorLog, Long>{

	VisitorLog getByTag(String visitorTag);

}
