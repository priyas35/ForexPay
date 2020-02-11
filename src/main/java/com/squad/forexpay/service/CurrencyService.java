package com.squad.forexpay.service;

import com.squad.forexpay.dto.ExchangeResponseDto;

public interface CurrencyService {
	
	ExchangeResponseDto exchangeCurrency(String from, String to, Double amount);

}
