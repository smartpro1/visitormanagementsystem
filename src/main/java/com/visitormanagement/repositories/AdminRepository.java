package com.visitormanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin getByUsernameOrEmail(String username, String email);

	Admin findByUsername(String username);

	Admin getById(Long id);

	Admin findByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
