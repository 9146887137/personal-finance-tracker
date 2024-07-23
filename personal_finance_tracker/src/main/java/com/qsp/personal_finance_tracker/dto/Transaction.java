package com.qsp.personal_finance_tracker.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int transactionId;
	private String decription;
	private String paymentMode;
	private String transactionType;
	private String transactionDate;
	private double transactionAmount;

}
