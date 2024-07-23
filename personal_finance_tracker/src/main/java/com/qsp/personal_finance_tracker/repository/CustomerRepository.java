package com.qsp.personal_finance_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.qsp.personal_finance_tracker.dto.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{


}
