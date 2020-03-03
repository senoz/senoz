package com.rabobank.customer.statementProcessor.services;

import org.springframework.stereotype.Service;

import com.rabobank.customer.statementProcessor.dtos.CustomerStatement;
import com.rabobank.customer.statementProcessor.utils.Response;

@Service
public interface CustomerStatementService {

	public Response processStatement(CustomerStatement cs) throws Exception;
}
