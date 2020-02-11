package com.squad.forexpay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.squad.forexpay.dto.TransactionRequestDto;
import com.squad.forexpay.entity.Account;
import com.squad.forexpay.entity.Currency;
import com.squad.forexpay.entity.User;
import com.squad.forexpay.entity.UserAccount;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.repository.AccountRepository;
import com.squad.forexpay.repository.UserAccountRepository;
import com.squad.forexpay.repository.UserRepository;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AccountServiceTest {
	
	@InjectMocks
	AccountServiceImpl accountServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	UserAccountRepository userAccountRepository;
	
	@Mock
	AccountRepository accountRepository;
	
	UserAccount userAccount = new UserAccount();
	
	User user= new User();
	User user1= new User();
	TransactionRequestDto transactionRequestDto= new TransactionRequestDto();
	Account account = new Account();
	@Before
	public void setup() {
		
		Currency currency = new Currency();
		currency.setCurrencyName("test");

		account.setAccountNumber(1L);
		account.setBalance(1.0);
		account.setBankName("test");
		account.setBranchName("test");
		account.setCurrency(currency);
		user.setUserId(1);
		
		userAccount.setAccount(account);
		userAccount.setUser(user);
		
		
		transactionRequestDto.setUserId(1);
		transactionRequestDto.setDestinationAccountNumber(1L);
		
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testGetAccountSummaryUserNotFoundException() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(null));
		accountServiceImpl.getAccountSummary(1);
	}
	
	@Test(expected = AccountnotFoundException.class)
	public void testGetAccountSummaryAccountnotFoundException() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(new User()));
		Mockito.when(userAccountRepository.findByUser(Mockito.any())).thenReturn(Optional.ofNullable(null));
		accountServiceImpl.getAccountSummary(1);
	}
	
	@Test
	public void testGetAccountSummarySuccess() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.ofNullable(new User()));
		Mockito.when(userAccountRepository.findByUser(Mockito.any())).thenReturn(Optional.ofNullable(userAccount));
		Long actual = accountServiceImpl.getAccountSummary(1).getAccountNumber();
		Long expected = 1L;
		assertEquals(expected, actual);
	}
	
	@Test(expected=UserNotFoundException.class)
	public void transferCurrencyUserNotFoundException() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(11)).thenReturn(Optional.of(user));
		accountServiceImpl.transferCurrency(transactionRequestDto);
	}
	
	@Test(expected=AccountnotFoundException.class)
	public void transferCurrencyAccountNotFoundException() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(userAccountRepository.findByUser(user1)).thenReturn(Optional.of(userAccount));
		accountServiceImpl.transferCurrency(transactionRequestDto);
	}
	
	@Test(expected=AccountnotFoundException.class)
	public void transferCurrencyDestinationAccountNotFound() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(userAccountRepository.findByUser(user)).thenReturn(Optional.of(userAccount));
		Mockito.when(accountRepository.findById(6L)).thenReturn(Optional.of(account));
		accountServiceImpl.transferCurrency(transactionRequestDto);
	}
	
	@Test
	public void transferCurrency() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(userAccountRepository.findByUser(user)).thenReturn(Optional.of(userAccount));
		Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
		accountServiceImpl.transferCurrency(transactionRequestDto);
	}
}
