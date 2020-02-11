package com.squad.forexpay.service;

import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;

public interface AccountService {
	
	AccountSummaryResponseDto getAccountSummary(Integer userId) throws UserNotFoundException, AccountnotFoundException;

}
