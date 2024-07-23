package com.qsp.personal_finance_tracker.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CustomerTransaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int customerTransactionId;
	private String description;
	private String transactionType;
	private String paymentMode;
	private String date;
	private double amount;

}
