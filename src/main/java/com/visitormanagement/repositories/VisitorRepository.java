package com.visitormanagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>{
   Visitor getByPhone(String phone);

List<Visitor> findByStaffOnDuty(String adminUsername);

Visitor findByPhone(String phone);

}
