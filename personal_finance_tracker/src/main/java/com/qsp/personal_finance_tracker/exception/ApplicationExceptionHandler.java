package com.qsp.personal_finance_tracker.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.qsp.personal_finance_tracker.util.ResponseStructure;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(AccountCreationException.class)
	public ResponseEntity<ResponseStructure<String>> handleAccountCreationException(AccountCreationException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("something wrong!");
		structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ResponseStructure<String>> handleConstraintViolationException(ConstraintViolationException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("details cant be null!");
		structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidPasswordException(InvalidPasswordException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("invalid password!");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotExistException.class)
	public ResponseEntity<ResponseStructure<String>> handleUserNotExistException(UserNotExistException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("User Not Exist!");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(InvalidTransactionStateException.class)
	public ResponseEntity<ResponseStructure<String>> handleInvalidTransactionStateException(InvalidTransactionStateException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("cant do debit transactio with transaction amount is greater than wallet amount!");
		structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(NegativeAmountException.class)
	public ResponseEntity<ResponseStructure<String>> handleNegativeAmountException(NegativeAmountException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("cant do debit transactio with transaction amount is greater than wallet amount!");
		structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ResponseStructure<String>> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("Already exist!");
		structure.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleCustomerNotFoundException(CustomerNotFoundException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("Customer Not found!");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(NoSuchCustomerException.class)
	public ResponseEntity<ResponseStructure<String>> handleNoSuchCustomerException(NoSuchCustomerException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("No Customer found!");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(NoSuchTransactionException.class)
	public ResponseEntity<ResponseStructure<String>> handleNoSuchTransactionException(NoSuchTransactionException e) {
		ResponseStructure<String>structure=new ResponseStructure<String>();
		structure.setMessage("No transactions found..!");
		structure.setStatus(HttpStatus.NOT_FOUND.value());
		structure.setData(e.getMessage());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.NOT_FOUND);
	}

}
