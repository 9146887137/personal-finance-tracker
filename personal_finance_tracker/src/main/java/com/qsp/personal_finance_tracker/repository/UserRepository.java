package com.qsp.personal_finance_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.personal_finance_tracker.dto.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByUserPhone(long phone);
	
}
