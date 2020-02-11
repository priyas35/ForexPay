package com.squad.forexpay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.ExchangeResponseDto;
import com.squad.forexpay.dto.RestExchangeDto;
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
	

	/**
	 * 
	 * @author Raghu.
	 * @since 2020-02-05. This method will get the exchange value for a particular
	 *        currency.
	 * @param fromCurrency, toCurrency and amount
	 * @return ExchangeResponseDto which contains exchange value, taxAmount and
	 *         total amount
	 * 
	 */
	
	@Override
	public ExchangeResponseDto exchangeCurrency(String from, String to, Double amount) {

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		RestExchangeDto exchange = restTemplate
				.exchange(Constant.EXCHANGE_API + from, HttpMethod.GET, entity, RestExchangeDto.class).getBody();

		ExchangeResponseDto exchangeResponseDto = new ExchangeResponseDto();
		Double convertedAmount = exchange.getRates().get(to) * amount;
		Double taxAmount;
		if (exchange.getRates().get(Constant.CURRENCY_CODE_USD) * amount < 500) {
			taxAmount = amount * 5 / 100;
		} else {
			taxAmount = amount * 7 / 100;
		}
		exchangeResponseDto.setConvertedAmount(convertedAmount);
		exchangeResponseDto.setServiceTax(taxAmount);
		exchangeResponseDto.setTotalAmount(amount + taxAmount);

		return exchangeResponseDto;
	}


}
