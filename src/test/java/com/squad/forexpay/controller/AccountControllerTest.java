package com.squad.forexpay.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.dto.ResponseDto;
import com.squad.forexpay.dto.TransactionRequestDto;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.service.AccountService;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountControllerTest {
	
	@InjectMocks
	AccountController accountController;
	
	@Mock
	AccountService accountService;
	
	TransactionRequestDto transactionRequestDto= new TransactionRequestDto();
	ResponseDto responseDto= new ResponseDto();
	
	@Before
	public void init() {

	}
	
	@Test
	public void testGetAccountSummary() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(accountService.getAccountSummary(1)).thenReturn(new AccountSummaryResponseDto());
		Integer actual = accountController.getAccountSummary(1).getStatusCodeValue();
		Integer expected = 200;
		assertEquals(expected, actual);
	}
	
	@Test
	public void testTransferCurrency() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(accountService.transferCurrency(transactionRequestDto)).thenReturn(responseDto);
		ResponseEntity<ResponseDto> responseDto=accountController.transferCurrency(transactionRequestDto);
		assertEquals(HttpStatus.OK.value(), responseDto.getStatusCode().value());
	}

}
