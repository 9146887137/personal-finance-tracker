package com.qsp.personal_finance_tracker.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.qsp.personal_finance_tracker.dto.Customer;
import com.qsp.personal_finance_tracker.dto.CustomerTransaction;
import com.qsp.personal_finance_tracker.dto.User;
import com.qsp.personal_finance_tracker.repository.CustomerRepository;
import com.qsp.personal_finance_tracker.repository.CustomerTransactionRepo;
import com.qsp.personal_finance_tracker.repository.UserRepository;

@Repository
public class UserDao {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerTransactionRepo customerTransactionRepo;
	
	
	public User creatAccount(User user) {
		return repository.save(user);
	}
	
	
	public User findByUserPhone(long phone) {
		return repository.findByUserPhone(phone);
	}
	/*
	 * this method  is used to add transaction*/
	
	public User saveUser(User user) {
		return repository.save(user);
		
	}

	public User updateProfile(User user) {
		return repository.save(user);
	}
	/*
	 * this return me the all the details of user*/
	public User getUser(long phone) {
		return repository.findByUserPhone(phone);
	}
	
	//findCustomer
	public Customer findCustomer(int customerId) {
		Optional<Customer> customer=customerRepository.findById(customerId);
		if(customer.isPresent()) {
			return customer.get();
		}
		return null;
	}
	
	//save customer
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	//
	public boolean deleteCustomer(int customerId) {
		Optional<Customer>  customer=customerRepository.findById(customerId);
		if(customer.isPresent()) {
			customerRepository.delete(customer.get());
			return true;
		}
	 return false;
	}

}
