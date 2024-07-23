package com.qsp.personal_finance_tracker.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qsp.personal_finance_tracker.dto.Customer;
import com.qsp.personal_finance_tracker.dto.CustomerTransaction;
import com.qsp.personal_finance_tracker.dto.Transaction;
import com.qsp.personal_finance_tracker.dto.User;
import com.qsp.personal_finance_tracker.dto.Wallet;
import com.qsp.personal_finance_tracker.service.UserService;
import com.qsp.personal_finance_tracker.util.ResponseStructure;

@RestController
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping("/signup/user")
	public ResponseEntity<ResponseStructure<String>> createAccount(@RequestBody User user) {
		return service.createAccount(user);
	}
	
	@GetMapping("/login/user/{phone}/{password}")
	public ResponseEntity<ResponseStructure<User>> createAccount(@PathVariable long phone,@PathVariable String password) {
		return service.login(phone, password);
	}
	@PutMapping("/add/transaction/{phone}")
	public ResponseEntity<ResponseStructure<User>> addTransaction(@PathVariable long phone,@RequestBody Transaction transaction){
		return service.addTransaction(phone, transaction);
	}
	
	@GetMapping("/find/wallet/{phone}")
	public ResponseEntity<ResponseStructure<Wallet>> getWallet(@PathVariable long phone){
		return service.getWallet(phone);
	}
	
	@GetMapping("/transactions/{phone}")
	public ResponseEntity<ResponseStructure<List<Transaction>>> getAllTransactions(@PathVariable long phone){
		return service.getAllTransactions(phone);
	}
	@PutMapping("/updateprofile/{phone}")
	public ResponseEntity<ResponseStructure<User>> updateProfile(@PathVariable long phone,@RequestBody User user){
		return service.updateProfile(phone, user);
	}
	@GetMapping("/profile/{phone}")
	public ResponseEntity<ResponseStructure<User>> getUser(@PathVariable long phone){
		return service.getUser(phone);
	}
	@GetMapping("/total/debit/amount/{phone}")
	public ResponseEntity<ResponseStructure<Double>> tatalDebited(@PathVariable long phone) {
		return service.totalDebited(phone);
	}
	@GetMapping("/total/credit/amount/{phone}")
	public ResponseEntity<ResponseStructure<Double>> tatalCredited(@PathVariable long phone) {
		return service.totalCredited(phone);
	}
	
	@PutMapping("/add/newcustomer/{phone}")
	public ResponseEntity<ResponseStructure<List<Customer>>> addCustomer(@PathVariable long phone,@RequestBody Customer customer) {
		return service.addCustomer(phone, customer);
	}
	@PutMapping("/add/customertransaction/{customerId}")	
	public ResponseEntity<ResponseStructure<Customer>> addCustomerTransaction(@PathVariable int customerId,@RequestBody CustomerTransaction customerTransaction) {
		return service.addCustomerTransaction(customerId, customerTransaction);
	}
	
	@GetMapping("/customer/{customerId}")	
	public ResponseEntity<ResponseStructure<Customer>> getCustomer(@PathVariable int customerId) {
		return service.getCustomer(customerId);
	}
	
	@DeleteMapping("/delete/customer/{customerId}")
	public ResponseEntity<ResponseStructure<String>> deleteCustomer(@PathVariable int customerId) {
		return service.deleteCustomer(customerId);
	}
	
	@GetMapping("/customer/transactionreport/{phone}")
	public ResponseEntity<ResponseStructure<Map<String, Double>>> deleteCustomer(@PathVariable long phone) {
		return service.customerTransactionReport(phone);
	}
	@GetMapping("/search/customer/{phone}/{data}")
	public ResponseEntity<ResponseStructure<List<Customer>>> searchCustomer(@PathVariable long phone,@PathVariable String data) {
		return service.searchCustomer(phone, data);
	}
	@GetMapping("/filter/transactions/{phone}/{month}")
	public ResponseEntity<ResponseStructure<List<Transaction>>> filterTransactions(@PathVariable long phone,@PathVariable String month) {
		return service.filterTransactions(phone, month);
	}
	

}
