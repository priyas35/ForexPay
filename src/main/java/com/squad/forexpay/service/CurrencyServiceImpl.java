package com.squad.forexpay.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.ExchangeResponseDto;
import com.squad.forexpay.dto.RestExchangeDto;

@Service
public class CurrencyServiceImpl implements CurrencyService {

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
