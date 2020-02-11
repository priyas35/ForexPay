package com.squad.forexpay.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import com.squad.forexpay.entity.Transaction;
import com.squad.forexpay.entity.User;
import com.squad.forexpay.entity.UserAccount;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.repository.AccountRepository;
import com.squad.forexpay.repository.TransactionRepository;
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

	@Mock
	TransactionRepository transactionRepository;
	

	UserAccount userAccount = new UserAccount();

	Account account = new Account();

	List<Transaction> transactions = new ArrayList<Transaction>();

	User user = new User();
	User user1 = new User();
	TransactionRequestDto transactionRequestDto = new TransactionRequestDto();


	@Before
	public void setup() {

		Currency currency = new Currency();
		currency.setCurrencyName("test");

		Account account = new Account();
		account.setAccountNumber(1L);
		account.setBalance(1.0);
		account.setBankName("test");
		account.setBranchName("test");
		account.setCurrency(currency);

		Transaction transaction = new Transaction();
		transaction.setAvailableBalance(1.0);
		transaction.setCurrency(currency);
		transaction.setDestinationAccountNumber(account);
		transaction.setSourceAccountNumber(account);
		transaction.setStatus("test");
		transaction.setTransactionAmount(1.0);
		transaction.setTransactionDate(LocalDateTime.now());
		transaction.setTransactionId(1L);
		transaction.setTransactionType("test");
		transactions.add(transaction);

		user.setUserId(1);

		userAccount.setAccount(account);
		userAccount.setUser(user);

		transactionRequestDto.setUserId(1);
		transactionRequestDto.setTransactionAmount(100D);
		transactionRequestDto.setDestinationAccountNumber(1L);

		userAccount.setAccount(account);

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

	@Test(expected = AccountnotFoundException.class)
	public void testGetMyTransactionsAccountnotFoundException() throws UserNotFoundException, AccountnotFoundException {

		Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(null));
		accountServiceImpl.getMyTransactions(1L);

	}

	@Test(expected = AccountnotFoundException.class)
	public void testGetMyTransactionsAccountnotFoundException1()
			throws UserNotFoundException, AccountnotFoundException {

		Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));
		Mockito.when(transactionRepository.findBySourceAccountNumber(Mockito.any())).thenReturn(new ArrayList<>());
		accountServiceImpl.getMyTransactions(1L);

	}

	@Test
	public void testGetMyTransactionsSuccess() throws UserNotFoundException, AccountnotFoundException {

		Mockito.when(accountRepository.findById(1L)).thenReturn(Optional.ofNullable(account));
		Mockito.when(transactionRepository.findBySourceAccountNumber(Mockito.any())).thenReturn(transactions);
		String actual = accountServiceImpl.getMyTransactions(1L).get(0).getStatus();
		String expected = "test";
		assertEquals(expected, actual);

	}

	@Test(expected = UserNotFoundException.class)
	public void transferCurrencyUserNotFoundException() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(11)).thenReturn(Optional.of(user));
		accountServiceImpl.transferCurrency(transactionRequestDto);
	}

	@Test(expected = AccountnotFoundException.class)
	public void transferCurrencyAccountNotFoundException() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(userAccountRepository.findByUser(user1)).thenReturn(Optional.of(userAccount));
		accountServiceImpl.transferCurrency(transactionRequestDto);
	}

	@Test(expected = AccountnotFoundException.class)
	public void transferCurrencyDestinationAccountNotFound() throws UserNotFoundException, AccountnotFoundException {
		Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(user));
		Mockito.when(userAccountRepository.findByUser(user)).thenReturn(Optional.of(userAccount));
		Mockito.when(accountRepository.findById(6L)).thenReturn(Optional.of(account));
		accountServiceImpl.transferCurrency(transactionRequestDto);
	}

}
