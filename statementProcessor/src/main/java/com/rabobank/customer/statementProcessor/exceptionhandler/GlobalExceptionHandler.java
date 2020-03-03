package com.rabobank.customer.statementProcessor.exceptionhandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rabobank.customer.statementProcessor.utils.Response;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(FormatFailureException.class)
	public Response ClientError() {
		Response res = new Response();
		res.setResult("BAD_REQUEST");
		return res;
	}
	
	@ExceptionHandler(Exception.class)
	public Response serverError() {
		Response res =new Response();
		res.setResult("INTERNAL_SERVER_ERROR");
		return res;
	}

}
