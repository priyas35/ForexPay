package com.squad.forexpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squad.forexpay.dto.ExchangeResponseDto;
import com.squad.forexpay.service.CurrencyService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/exchange")
@Slf4j
public class CurrencyController {
	
	@Autowired
	CurrencyService currencyService;

	/**
	 * 
	 * @author Raghu.
	 * @since 2020-02-05. This method will get the exchange value for a particular currency.
	 * @param fromCurrency, toCurrency and amount
	 * @return ExchangeResponseDto which contains exchange value, taxAmount and total amount
	 * 
	 */
	@GetMapping
	public ResponseEntity<ExchangeResponseDto> getExchangeValue(@RequestParam("from") String from,
			@RequestParam("to") String to, @RequestParam("amount") Double amount) {
		log.info("CurrencyController getExchangeValue method ----> getting exchange value");
		return ResponseEntity.ok().body(currencyService.exchangeCurrency(from, to, amount));
	}

}
