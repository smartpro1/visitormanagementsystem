package com.visitormanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin getByUsernameOrEmail(String username, String password);

	Admin findUserByUsername(String username);

	Admin getById(Long id);

}
