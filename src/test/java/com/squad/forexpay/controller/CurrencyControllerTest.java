package com.squad.forexpay.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.squad.forexpay.dto.ExchangeResponseDto;
import com.squad.forexpay.entity.Currency;
import com.squad.forexpay.exception.CurrencyNotFoundException;
import com.squad.forexpay.service.CurrencyService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CurrencyControllerTest {

	@InjectMocks
	CurrencyController currencyController;

	@Mock
	CurrencyService currencyService;

	@Test
	public void testGetExchangeValue() {
		Mockito.when(currencyService.exchangeCurrency("test", "test", 1.0)).thenReturn(new ExchangeResponseDto());
		Integer actual = currencyController.getExchangeValue("test", "test", 1.0).getStatusCodeValue();
		Integer expected = 200;
		assertEquals(expected, actual);
	}

	@Test
	public void testGetAllCurrency() throws CurrencyNotFoundException {
		Mockito.when(currencyService.getAllCurrency()).thenReturn(new ArrayList<>());
		ResponseEntity<List<Currency>> actual = currencyController.getAllCurrency();
		assertEquals(HttpStatus.OK, actual.getStatusCode());
	}

}
