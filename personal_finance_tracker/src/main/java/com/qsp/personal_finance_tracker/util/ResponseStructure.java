package com.qsp.personal_finance_tracker.util;

import lombok.Data;

@Data
public class ResponseStructure<T>{

	private String message;
	private int status;
	private T data;
}
