package com.squad.forexpay.service;

import java.util.List;

import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.dto.ResponseDto;
import com.squad.forexpay.dto.TransactionDetailsDto;
import com.squad.forexpay.dto.TransactionRequestDto;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;

public interface AccountService {
	
	AccountSummaryResponseDto getAccountSummary(Integer userId) throws UserNotFoundException, AccountnotFoundException;
	
	List<TransactionDetailsDto> getMyTransactions(Long accountNumber) throws AccountnotFoundException;
	
	ResponseDto transferCurrency(TransactionRequestDto transactionRequestDto) throws UserNotFoundException, AccountnotFoundException;
}
