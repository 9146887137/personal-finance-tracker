package com.qsp.personal_finance_tracker.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.qsp.personal_finance_tracker.dao.UserDao;
import com.qsp.personal_finance_tracker.dto.Customer;
import com.qsp.personal_finance_tracker.dto.CustomerTransaction;
import com.qsp.personal_finance_tracker.dto.Transaction;
import com.qsp.personal_finance_tracker.dto.User;
import com.qsp.personal_finance_tracker.dto.Wallet;
import com.qsp.personal_finance_tracker.exception.CustomerNotFoundException;
import com.qsp.personal_finance_tracker.exception.InvalidPasswordException;
import com.qsp.personal_finance_tracker.exception.InvalidTransactionStateException;
import com.qsp.personal_finance_tracker.exception.NegativeAmountException;
import com.qsp.personal_finance_tracker.exception.NoSuchCustomerException;
import com.qsp.personal_finance_tracker.exception.NoSuchTransactionException;
import com.qsp.personal_finance_tracker.exception.UserNotExistException;
import com.qsp.personal_finance_tracker.util.ResponseStructure;

@Service
public class UserService {
	
	@Autowired
	private UserDao dao;
	
	public ResponseEntity<ResponseStructure<String>> createAccount(User user) {
		User dbUser=dao.creatAccount(user);
		ResponseStructure<String> structure=new ResponseStructure<String>();
			structure.setMessage("signup successfully!");
			structure.setStatus(HttpStatus.CREATED.value());
			structure.setData("user registered");
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.CREATED);
	}
	
	public ResponseEntity<ResponseStructure<User>> login(long phone,String password) {
		User user=dao.findByUserPhone(phone);
		ResponseStructure<User> structure=new ResponseStructure<User>();
		if(user!=null) {
			if(user.getPassword().equals(password)) {
				structure.setMessage("login successfully!");
				structure.setStatus(HttpStatus.FOUND.value());
				structure.setData(user);
			return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.FOUND);
			}
			throw new InvalidPasswordException("invalid password!");
		}
		throw new UserNotExistException("user not exist!");
	}
	
	/**
	 * this method is used to add the {debit,credit transaction based on transaction type using debit method we 
	 * get InvalidTransactionStateException when wallet amount is less than transaction amount}
	 * */
	public ResponseEntity<ResponseStructure<User>> addTransaction(long phone,Transaction transaction) {
		User user=dao.findByUserPhone(phone);
		ResponseStructure<User> structure=new ResponseStructure<User>();
		if(user!=null) {
			if(transaction.getTransactionAmount()>0) {
				if(transaction.getTransactionType().equalsIgnoreCase("credit")) {
						user.getWallet().setWalletAmount(user.getWallet().getWalletAmount()+transaction.getTransactionAmount());
						user.getWallet().getTransactions().add(transaction);
						LocalDate date=LocalDate.now();
						transaction.setTransactionDate(String.valueOf(date));
						structure.setMessage("transaction added !");
						structure.setData(dao.saveUser(user));		
						structure.setStatus(HttpStatus.FOUND.value());
						return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.FOUND);
				}
					if(user.getWallet().getWalletAmount()>=transaction.getTransactionAmount()) {
						user.getWallet().setWalletAmount(user.getWallet().getWalletAmount()-transaction.getTransactionAmount());
						user.getWallet().getTransactions().add(transaction);
						LocalDate date=LocalDate.now();
						transaction.setTransactionDate(String.valueOf(date));
						structure.setMessage("transaction added !");
						structure.setData(dao.saveUser(user));		
						structure.setStatus(HttpStatus.FOUND.value());
						return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.FOUND);
					}
					throw new InvalidTransactionStateException("you cant add debit entry if wallet amount is less than amt");
			}
			throw new NegativeAmountException("invalid amount");
		}
		throw new UserNotExistException("user not found");
	}
	
	public ResponseEntity<ResponseStructure<Wallet>> getWallet(long phone) {
		User user=dao.findByUserPhone(phone);
		if(user!=null) {
			ResponseStructure<Wallet> structure=new ResponseStructure<Wallet>();
				structure.setMessage("wallet fetched !");
				structure.setData(user.getWallet());		
				structure.setStatus(HttpStatus.FOUND.value());
				return new ResponseEntity<ResponseStructure<Wallet>>(structure,HttpStatus.FOUND);
			}
			throw new UserNotExistException("no not found..!");
	}
		
	
	public ResponseEntity<ResponseStructure<List<Transaction>>> getAllTransactions(long phone) {
		User user=dao.findByUserPhone(phone);
		if(user!=null) {
			List<Transaction> transactions=user.getWallet().getTransactions();
			ResponseStructure<List<Transaction>> structure=new ResponseStructure<List<Transaction>>();
			if(!transactions.isEmpty()) {
				structure.setMessage("tranaction fetched !");
				structure.setData(transactions);		
				structure.setStatus(HttpStatus.FOUND.value());
				return new ResponseEntity<ResponseStructure<List<Transaction>>>(structure,HttpStatus.FOUND);
			}
			throw new NoSuchTransactionException("no transaction found");
		}
		throw new UserNotExistException("no not found..!");
	}
	public ResponseEntity<ResponseStructure<User>> updateProfile(long phone,User user) {
		User dbUser=dao.findByUserPhone(phone);
		ResponseStructure<User> structure=new ResponseStructure<User>();
		if(dbUser!=null) {
			dbUser.setUserName(user.getUserName());
			dbUser.setUserAddress(user.getUserAddress());
			dbUser.setPassword(user.getPassword());
			dbUser.setUserEmail(user.getUserEmail());
			structure.setMessage("Profile updated !");
			structure.setData(dao.updateProfile(dbUser));		
			structure.setStatus(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.CREATED);
		}
		throw new UserNotExistException("user not found");
	}
	
	public ResponseEntity<ResponseStructure<User>> getUser(long phone) {
		User dbUser=dao.getUser(phone);
		ResponseStructure<User> structure=new ResponseStructure<User>();
		if(dbUser!=null) {
			structure.setMessage("user fetched !");
			structure.setData(dbUser);		
			structure.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<User>>(structure,HttpStatus.FOUND);
		}
		throw new UserNotExistException("user not found");
	}
	
	/**
	 * this method return the total amount of transactions according to debit first it check the if there is any transaction present
	 * with tranasactions type {debit} if not present it return {0.0}*/
	public ResponseEntity<ResponseStructure<Double>> totalDebited(long phone) {
		User dbUser=dao.findByUserPhone(phone);
		ResponseStructure<Double> structure=new ResponseStructure<Double>();
		if(dbUser!=null) {
			Wallet wallet=dbUser.getWallet();
				List<Transaction> transactions=wallet.getTransactions().stream().filter((transaction)->transaction.getTransactionType().equalsIgnoreCase("debit")).collect(Collectors.toList());
					if(!transactions.isEmpty()) {
						structure.setMessage("user fetched !");
						structure.setData(transactions.stream().mapToDouble(i->i.getTransactionAmount()).sum());		
						structure.setStatus(HttpStatus.FOUND.value());
						return new ResponseEntity<ResponseStructure<Double>>(structure,HttpStatus.FOUND);
					}
					structure.setMessage("user fetched !");
					structure.setData(0.0);		
					structure.setStatus(HttpStatus.FOUND.value());
					return new ResponseEntity<ResponseStructure<Double>>(structure,HttpStatus.FOUND);
		}
		throw new UserNotExistException("user not found");
	}
	
	public ResponseEntity<ResponseStructure<Double>> totalCredited(long phone) {
		User dbUser=dao.findByUserPhone(phone);
		ResponseStructure<Double> structure=new ResponseStructure<Double>();
		if(dbUser!=null) {
			Wallet wallet=dbUser.getWallet();
				List<Transaction> transactions=wallet.getTransactions().stream().filter((transaction)->transaction.getTransactionType().equalsIgnoreCase("credit")).collect(Collectors.toList());
					if(!transactions.isEmpty()) {
						structure.setMessage("user fetched !");
						structure.setData(transactions.stream().mapToDouble(i->i.getTransactionAmount()).sum());		
						structure.setStatus(HttpStatus.FOUND.value());
						return new ResponseEntity<ResponseStructure<Double>>(structure,HttpStatus.FOUND);
					}
					structure.setMessage("user fetched !");
					structure.setData(0.0);		
					structure.setStatus(HttpStatus.FOUND.value());
					return new ResponseEntity<ResponseStructure<Double>>(structure,HttpStatus.FOUND);
		}
		throw new UserNotExistException("user not found");
	}
	public ResponseEntity<ResponseStructure<List<Customer>>> addCustomer(long phone,Customer customer) {
		User user=dao.findByUserPhone(phone);
		ResponseStructure<List<Customer>> structure=new ResponseStructure<List<Customer>>();
		if(user!=null) {
			customer.setTotalAmount(0);
			user.getCustomers().add(customer);
			structure.setMessage("Customer added successfully!");
			structure.setData(dao.saveUser(user).getCustomers());		
			structure.setStatus(HttpStatus.CREATED.value());
			return new ResponseEntity<ResponseStructure<List<Customer>>>(structure,HttpStatus.CREATED);
		}
		throw new UserNotExistException("user not found");
	}
	/*
	 * this method is used to add transaction of existing user with total remaining balance*/
	
	public ResponseEntity<ResponseStructure<Customer>> addCustomerTransaction(int customerId,CustomerTransaction customerTransaction) {
		Customer customer=dao.findCustomer(customerId);
		ResponseStructure<Customer> structure=new ResponseStructure<Customer>();
		if(customer!=null) {
				if(customerTransaction.getAmount()>0) {
					if(customerTransaction.getTransactionType().equalsIgnoreCase("yougave")) {
						customer.setTotalAmount(customer.getTotalAmount()+customerTransaction.getAmount());
					}
					else {
						customer.setTotalAmount(customer.getTotalAmount()-customerTransaction.getAmount());
					}
					LocalDate date=LocalDate.now();
					customerTransaction.setDate(String.valueOf(date));
					customer.getCustomerTransactions().add(customerTransaction);
					structure.setMessage("transaction added..!");
					structure.setData(dao.saveCustomer(customer));		
					structure.setStatus(HttpStatus.CREATED.value());
					return new ResponseEntity<ResponseStructure<Customer>>(structure,HttpStatus.CREATED);
			}
				throw new NegativeAmountException("Invalid amount..!");
		}
		throw new CustomerNotFoundException("customer not found.!");
	}
	
	//get customers details based on customer id
	public ResponseEntity<ResponseStructure<Customer>> getCustomer(int customerId) {
		Customer customer=dao.findCustomer(customerId);
		ResponseStructure<Customer> structure=new ResponseStructure<Customer>();
		if(customer!=null) {
			structure.setMessage("customer fetched !");
			structure.setData(customer);		
			structure.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<Customer>>(structure,HttpStatus.FOUND);
		}
		throw new CustomerNotFoundException("customer not found..!");
	}
	
	
	public ResponseEntity<ResponseStructure<String>> deleteCustomer(int customerId) {
		ResponseStructure<String> structure=new ResponseStructure<String>();
		if(dao.deleteCustomer(customerId)){
			structure.setMessage("customer deleted!");
			structure.setData("customer deleted successfully!");		
			structure.setStatus(HttpStatus.FOUND.value());
			return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.FOUND);
		}
		throw new CustomerNotFoundException("customer not found");
	}
	
	public ResponseEntity<ResponseStructure<Map<String, Double>>> customerTransactionReport(long phone) {
		User user=dao.findByUserPhone(phone);
		if(user!=null) {
			if(!user.getCustomers().isEmpty()){
			ResponseStructure<Map<String, Double>> structure=new ResponseStructure<Map<String, Double>>();
			Double give=user.getCustomers().stream().filter((tr)->tr.getTotalAmount()<0).mapToDouble((amt)->amt.getTotalAmount()).sum();
			Double get=user.getCustomers().stream().filter((tr)->tr.getTotalAmount()>0).mapToDouble((amt)->amt.getTotalAmount()).sum();
			HashMap<String, Double> map=new HashMap<String,Double>();
			map.put("You Will Give", give);
			map.put("You Will Get", get);
			
			structure.setMessage("Report fetched");
			structure.setData(map);		
			structure.setStatus(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<Map<String, Double>>>(structure,HttpStatus.OK);
		}
		throw new NoSuchCustomerException("no customers found");
			
		}
		throw new UserNotExistException("UserNotExist");
		
	}
	
	public ResponseEntity<ResponseStructure<List<Customer>>> searchCustomer(long phone,String data) {
	User user=dao.findByUserPhone(phone);
	ResponseStructure<List<Customer>> structure=new ResponseStructure<List<Customer>>();
	if(user!=null) {
		if(!user.getCustomers().isEmpty()) {
			structure.setMessage("customer found");
			structure.setData(user.getCustomers().stream().filter(cust->cust.getCustomerName().toLowerCase().contains(data.toLowerCase())||String.valueOf(cust.getCustomerPhone()).contains(data)).collect(Collectors.toList()));		
			structure.setStatus(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<List<Customer>>>(structure,HttpStatus.OK);
		}
		throw new NoSuchCustomerException("no customers found");
	}
	throw new UserNotExistException("UserNotExist");
	
	}
	
	public ResponseEntity<ResponseStructure<List<Transaction>>> filterTransactions(long phone,String month) {
		User user=dao.findByUserPhone(phone);
		ResponseStructure<List<Transaction>> structure=new ResponseStructure<List<Transaction>>();
		if(user!=null) {
			if(!user.getCustomers().isEmpty()){
				structure.setMessage("transactions fetched");
				structure.setData(user.getWallet().getTransactions().stream().filter((tr)->tr.getTransactionDate().substring(5, 7).equals(month)).collect(Collectors.toList()));;		
				structure.setStatus(HttpStatus.OK.value());
				return new ResponseEntity<ResponseStructure<List<Transaction>>>(structure,HttpStatus.OK);
			}
			throw new NoSuchCustomerException("no customer found..!");
		}
		throw new UserNotExistException("user not found..!");		
    }
}
