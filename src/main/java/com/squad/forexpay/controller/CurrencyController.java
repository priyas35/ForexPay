package com.squad.forexpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.squad.forexpay.dto.ExchangeResponseDto;
import com.squad.forexpay.entity.Currency;
import com.squad.forexpay.exception.CurrencyNotFoundException;
import com.squad.forexpay.service.CurrencyService;

import lombok.extern.slf4j.Slf4j;



@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RestController
@RequestMapping("/currencies")
@Slf4j
public class CurrencyController {

	@Autowired
	CurrencyService currencyService;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-11. This method will get all the currency codes and names.
	 * @return List of currency - list of currency code and description
	 * @throws CurrencyNotFoundException it will throw the exception if the currency
	 *                                   is not there.
	 * 
	 */
	@GetMapping
	public ResponseEntity<List<Currency>> getAllCurrency() throws CurrencyNotFoundException{
		log.info("Entering into CurrencyController getAllCurrency:calling CurrencyService");
		return new ResponseEntity<>(currencyService.getAllCurrency(),HttpStatus.OK);
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
	@GetMapping("/exchange")
	public ResponseEntity<ExchangeResponseDto> getExchangeValue(@RequestParam("from") String from,
			@RequestParam("to") String to, @RequestParam("amount") Double amount) {
		log.info("CurrencyController getExchangeValue method ----> getting exchange value");
		return ResponseEntity.ok().body(currencyService.exchangeCurrency(from, to, amount));
	}

}
