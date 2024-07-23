package com.qsp.personal_finance_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qsp.personal_finance_tracker.dto.CustomerTransaction;

public interface CustomerTransactionRepo extends JpaRepository<CustomerTransaction, List<CustomerTransaction>>{

}
