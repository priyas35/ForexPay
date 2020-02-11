package com.squad.forexpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.forexpay.entity.Currency;
import com.squad.forexpay.exception.CurrencyNotFoundException;
import com.squad.forexpay.service.CurrencyService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/currencies")
@CrossOrigin
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

}
