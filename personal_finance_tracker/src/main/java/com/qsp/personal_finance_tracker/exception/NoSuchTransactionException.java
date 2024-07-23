package com.qsp.personal_finance_tracker.exception;

public class NoSuchTransactionException extends RuntimeException{

	public NoSuchTransactionException() {
		// TODO Auto-generated constructor stub
	}
	
	public NoSuchTransactionException(String msg) {
		super(msg);
	}
}
