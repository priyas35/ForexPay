package com.squad.forexpay.service;

import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.dto.ResponseDto;
import com.squad.forexpay.dto.TransactionRequestDto;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;

public interface AccountService {
	
	AccountSummaryResponseDto getAccountSummary(Integer userId) throws UserNotFoundException, AccountnotFoundException;

	ResponseDto transferCurrency(TransactionRequestDto transactionRequestDto) throws UserNotFoundException, AccountnotFoundException;
}
