package com.rabobank.customer.statementProcessor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rabobank.customer.statementProcessor.dtos.CustomerStatement;
import com.rabobank.customer.statementProcessor.exceptionhandler.FormatFailureException;
import com.rabobank.customer.statementProcessor.services.CustomerStatementService;
import com.rabobank.customer.statementProcessor.utils.Response;

@RestController
@RequestMapping("/customer")
public class CustomerStatementProcessorController {
	
	@Autowired
	private CustomerStatementService cutomerService;
	
	@PostMapping("/process-statement")
	public Response processStatement(@RequestBody CustomerStatement customerStatement) throws Exception{
		Response res = null;
		try{
			res = cutomerService.processStatement(customerStatement);
		}catch(Exception ex){
			throw new FormatFailureException();
		}
		return res;
	}

}
