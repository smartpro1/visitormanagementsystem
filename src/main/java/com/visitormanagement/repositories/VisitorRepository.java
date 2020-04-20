package com.visitormanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.Visitor;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long>{
   Visitor getByPhone(String phone);

//com.visitormanagement.services.Visitor findByPhone(int phone);
}
