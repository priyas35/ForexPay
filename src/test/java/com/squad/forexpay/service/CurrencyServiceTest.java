package com.squad.forexpay.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.forexpay.entity.Currency;
import com.squad.forexpay.exception.CurrencyNotFoundException;
import com.squad.forexpay.repository.CurrencyRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CurrencyServiceTest {

	@InjectMocks
	CurrencyServiceImpl currencyServiceImpl;

	@Mock
	CurrencyRepository currencyRepository;

	Currency currency = new Currency();
	List<Currency> currencies = new ArrayList<>();

	@Before
	public void setUp() {
		currency.setCode("ABC");
		currencies.add(currency);
	}

	@Test
	public void testGetAllCurrency() throws CurrencyNotFoundException {
		Mockito.when(currencyRepository.findAll()).thenReturn(currencies);
		List<Currency> actual = currencyServiceImpl.getAllCurrency();
		assertEquals(1, actual.size());
	}

	@Test(expected = CurrencyNotFoundException.class)
	public void testGetAllCurrencyForCurrencyNotFoundException() throws CurrencyNotFoundException {
		currencies = new ArrayList<>();
		Mockito.when(currencyRepository.findAll()).thenReturn(currencies);
		currencyServiceImpl.getAllCurrency();
	}

	@Test
	public void testExchangeCurrencyLessInterest() {
		assertNotNull(currencyServiceImpl.exchangeCurrency("USD", "INR", 10.0));
	}

	@Test
	public void testExchangeCurrencyMoreInterest() {
		assertNotNull(currencyServiceImpl.exchangeCurrency("USD", "INR", 511.0));
	}

}
