package com.squad.forexpay.service;

import java.util.List;

import com.squad.forexpay.dto.ExchangeResponseDto;
import com.squad.forexpay.entity.Currency;
import com.squad.forexpay.exception.CurrencyNotFoundException;

public interface CurrencyService {
	
	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-11. This method will get all the currency codes and names.
	 * @return List of currency - list of currencyCode and description
	 * @throws CurrencyNotFoundException it will throw the exception if the currency
	 *                                   is not there.
	 * 
	 */
	List<Currency> getAllCurrency() throws CurrencyNotFoundException;
	
	ExchangeResponseDto exchangeCurrency(String from, String to, Double amount);
}
	