package com.rabobank.customer.statementProcessor.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rabobank.customer.statementProcessor.dtos.CustomerStatement;
import com.rabobank.customer.statementProcessor.repositories.CustomerStatementRepo;
import com.rabobank.customer.statementProcessor.services.CustomerStatementService;
import com.rabobank.customer.statementProcessor.utils.ErrorMessage;
import com.rabobank.customer.statementProcessor.utils.Response;
import com.rabobank.customer.statementProcessor.utils.VerificationStatus;

@Service
public class CustomerStatementServiceImpl implements CustomerStatementService {

	@Autowired
	CustomerStatementRepo repo;
	

	@Override
	public Response processStatement(CustomerStatement cs) throws Exception {
		
		Response res = new Response();
		List<ErrorMessage> errors = new ArrayList<ErrorMessage>();
		boolean isduplicate = false;
		boolean isincorrectBalance = false;
		
		try {
			if (repo.findById(cs.getTransactionReference()).isPresent()) {
				isduplicate = true;
				errors.add(new ErrorMessage(cs.getTransactionReference(), cs.getAccountNumber()));
				res.setResult(VerificationStatus.DUPLICATE_REFERENCE.toString());
				res.setErrorRecords(errors);
			}
			if ((cs.getStartBalance() + cs.getMutation() ) != cs.getEndBalance()) {
				isincorrectBalance = true;
				errors.add(new ErrorMessage(cs.getTransactionReference(), cs.getAccountNumber()));
				res.setResult(VerificationStatus.INCORRECT_END_BALANCE.toString());
				res.setErrorRecords(errors);
			}
			if (isduplicate && isincorrectBalance) {
				res.setResult(VerificationStatus.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE.toString());
				res.setErrorRecords(errors);
			}
			if (!isduplicate && !isincorrectBalance) {
				repo.save(cs);
				res.setResult(VerificationStatus.SUCCESSFUL.toString());
			}
		} catch (Exception ex) {
			throw new Exception();
		}
		return res;

	}

}
