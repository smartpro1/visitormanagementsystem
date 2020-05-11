package com.visitormanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.visitormanagement.models.PasswordReset;

@Repository
public interface PasswordResetRepository  extends JpaRepository<PasswordReset, Long>{
	PasswordReset findByResetToken(String token);
}
