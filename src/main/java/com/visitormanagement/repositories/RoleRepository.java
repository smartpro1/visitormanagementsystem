package com.visitormanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.Role;
import com.visitormanagement.models.RoleName;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long>{
  Role findByName(RoleName roleName);
}
