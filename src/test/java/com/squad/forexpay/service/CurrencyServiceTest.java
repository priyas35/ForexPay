package com.squad.forexpay.service;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CurrencyServiceTest {
	
	@InjectMocks
	CurrencyServiceImpl currencyServiceImpl;
	
	@Test
	public void testExchangeCurrencyLessInterest() {
		assertNotNull(currencyServiceImpl.exchangeCurrency("USD", "INR", 10.0));	
	}
	
	@Test
	public void testExchangeCurrencyMoreInterest() {
		assertNotNull(currencyServiceImpl.exchangeCurrency("USD", "INR", 511.0));	
	}

}
