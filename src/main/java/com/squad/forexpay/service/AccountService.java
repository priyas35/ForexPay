package com.squad.forexpay.service;

import com.squad.forexpay.dto.ResponseDto;
import com.squad.forexpay.dto.TransactionRequestDto;

public interface AccountService {
	public ResponseDto transferCurrency(TransactionRequestDto transactionRequestDto);
}
