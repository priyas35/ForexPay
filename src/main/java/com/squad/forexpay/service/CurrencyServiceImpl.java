package com.squad.forexpay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.entity.Currency;
import com.squad.forexpay.exception.CurrencyNotFoundException;
import com.squad.forexpay.repository.CurrencyRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {

	@Autowired
	CurrencyRepository currencyRepository;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-11. This method will get all the currency codes and names.
	 * @return List of currency - list of currencyCode and description
	 * @throws CurrencyNotFoundException it will throw the exception if the currency
	 *                                   is not there.
	 * 
	 */
	@Override
	public List<Currency> getAllCurrency() throws CurrencyNotFoundException {
		List<Currency> currencies = currencyRepository.findAll();
		if (currencies.isEmpty()) {
			log.info("Exception occurred in currencyServiceImpl getAllCurrency method:"+Constant.CURRENCY_NOT_FOUND);
			throw new CurrencyNotFoundException(Constant.CURRENCY_NOT_FOUND);
		} else {
			log.info("Entering into currencyServiceImpl getAllCurrency method: getting all currencies");
			return currencies;
		}
	}

}
