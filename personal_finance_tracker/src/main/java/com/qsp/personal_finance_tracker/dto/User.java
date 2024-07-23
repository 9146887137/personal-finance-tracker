package com.qsp.personal_finance_tracker.dto;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	private String userName;
	@Column(unique = true)
	private long userPhone;
	@Column(unique = true)
	private String userEmail;
	private String userAddress;
	private String password;
	
	@OneToOne(cascade = CascadeType.ALL) //because one with has one wallet
	private Wallet wallet;
	
	@OneToMany(cascade = CascadeType.ALL) //
	private List<Customer> customers;
	
	

}
