package com.squad.forexpay.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.dto.ExchangeResponseDto;
import com.squad.forexpay.dto.ResponseDto;
import com.squad.forexpay.dto.TransactionDetailsDto;
import com.squad.forexpay.dto.TransactionRequestDto;
import com.squad.forexpay.entity.Account;
import com.squad.forexpay.entity.Transaction;
import com.squad.forexpay.entity.User;
import com.squad.forexpay.entity.UserAccount;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.MinimumBalanceException;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.repository.AccountRepository;
import com.squad.forexpay.repository.TransactionRepository;
import com.squad.forexpay.repository.UserAccountRepository;
import com.squad.forexpay.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	TransactionRepository transactionRepository;

	/**
	 * This method is used to do fund transfer from one account to another account
	 * 
	 * @author Raghu M
	 * @param userId- id of the current user
	 * @return AccountSummaryResponseDto - it will give the current account summary details
	 * @throws UserNotFoundException-   thrown when the userId is invalid
	 * @throws AccountnotFoundException - thrown when the source/destination account
	 *                                  is invalid
	 */
	@Override
	public AccountSummaryResponseDto getAccountSummary(Integer userId) throws UserNotFoundException, AccountnotFoundException {
		User user = new User();
		user.setUserId(userId);
		if(!userRepository.findById(userId).isPresent()) {
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		}
		Optional<UserAccount> userAccount = userAccountRepository.findByUser(user);
		if(!userAccount.isPresent()) {
			throw new AccountnotFoundException(Constant.ACCOUNT_NOT_FOUND);
		}
		AccountSummaryResponseDto accountSummaryResponseDto = new AccountSummaryResponseDto();
		BeanUtils.copyProperties(userAccount.get().getAccount(), accountSummaryResponseDto);
		accountSummaryResponseDto.setCurrencyType(userAccount.get().getAccount().getCurrency().getCurrencyName());
		accountSummaryResponseDto.setMinimumBalance(1000.0);
		return accountSummaryResponseDto;
	}
	
	/**
	 * This method is used to do fund transfer from one account to another account
	 * 
	 * @author chethana
	 * @param transactionRequestDto- Intakes transaction details
	 * @return ResponseDto
	 * @throws UserNotFoundException- thrown when the userId is invalid
	 * @throws AccountnotFoundException - thrown when the source/destination account is invalid
	 */
	public ResponseDto transferCurrency(TransactionRequestDto transactionRequestDto) throws UserNotFoundException, AccountnotFoundException {
		log.info("Entering into transferCurrency() of AccountServiceImpl");
		Optional<User> userRespnse=userRepository.findById(transactionRequestDto.getUserId());
		if(!userRespnse.isPresent()) {
			log.error("Exception occured in transferCurrency of AccountServiceImpl:");
			throw new UserNotFoundException("User Not found");
		}
		Optional<UserAccount> sourceAccountResponse=userAccountRepository.findByUser(userRespnse.get());
		if(!sourceAccountResponse.isPresent()) {
			log.error("Exception occured in transferCurrency() of AccountServiceImpl:");
			throw new AccountnotFoundException("Source Account Not found");
		}
		Optional<Account> destinationAccountResponse=accountRepository.findById(transactionRequestDto.getDestinationAccountNumber());
		if(!destinationAccountResponse.isPresent()) {
			log.error("Exception occured in transferCurrency() of AccountServiceImpl:");
			throw new AccountnotFoundException("Destination Account Not found");
		}
				//update the debit account details
				Transaction debitTransaction= new Transaction();
				debitTransaction.setCurrency(sourceAccountResponse.get().getAccount().getCurrency());
				debitTransaction.setDestinationAccountNumber(destinationAccountResponse.get());
				debitTransaction.setSourceAccountNumber(sourceAccountResponse.get().getAccount());
				debitTransaction.setTransactionAmount(transactionRequestDto.getTransactionAmount());
				debitTransaction.setTransactionDate(LocalDateTime.now());
				debitTransaction.setTransactionType(Constant.DEBIT);
				debitTransaction.setAvailableBalance(sourceAccountResponse.get().getAccount().getBalance()-transactionRequestDto.getTransactionAmount());
				debitTransaction.setStatus(Constant.TRANSACTION_STATUS_PENDING);
				transactionRepository.save(debitTransaction);
				log.debug("Successfully updated the debit account details");
		return new ResponseDto();
	}
	

	/**
	 * This method is used to update the status of the pending records and completes transaction
	 * 
	 * @author Chethana
	 * @throws MinimumBalanceException- when the minimum balance is not maintained for fund transfer
	 */
	@Scheduled(cron="0 */2 * ? * *")//For every 2 minutes
	public void updateTransferCurrency() throws MinimumBalanceException {
		log.info("Entering into updateTransferCurrency() of AccountServiceImpl");
		Optional<List<Transaction>> transactionResponse=transactionRepository.findByStatus(Constant.TRANSACTION_STATUS_PENDING);
		if(!transactionResponse.isPresent()) {
			log.debug("No Pending jobs available");
		}
		else {
		for(Transaction transactionIndex:transactionResponse.get()) {

			Account destinationAccount=transactionIndex.getDestinationAccountNumber();
			Account sourceAccount=transactionIndex.getSourceAccountNumber();
			List<Account> accountResponseList= new ArrayList<>();
			if(destinationAccount==sourceAccount) {
				transactionIndex.setStatus(Constant.TRANSACTION_STATUS_CANCELLED);
				throw new MinimumBalanceException("Source and Destination should be different");
			}
			if(sourceAccount.getBalance()<Constant.MINIMUM_BALANCE) {
				transactionIndex.setStatus(Constant.TRANSACTION_STATUS_CANCELLED);
				throw new MinimumBalanceException("Minimum Balance Should be maintained");
			}
			CurrencyServiceImpl currencyServiceImpl=new CurrencyServiceImpl();
			ExchangeResponseDto exchangeResponseDto= currencyServiceImpl.exchangeCurrency(sourceAccount.getCurrency().getCode(), destinationAccount.getCurrency().getCode(), transactionIndex.getTransactionAmount());

			//debit account
			destinationAccount.setBalance(destinationAccount.getBalance()-exchangeResponseDto.getTotalAmount());
			//credit account
			sourceAccount.setBalance(sourceAccount.getBalance()+exchangeResponseDto.getConvertedAmount());
			accountResponseList.add(destinationAccount);
			accountResponseList.add(sourceAccount);
			accountRepository.saveAll(accountResponseList);
	
			transactionIndex.setStatus(Constant.TRANSACTION_STATUS_SUCCESS);
			transactionRepository.save(transactionIndex);
			
			//update the credit account details
			Transaction creditTransaction= new Transaction();
			creditTransaction.setCurrency(destinationAccount.getCurrency());
			creditTransaction.setDestinationAccountNumber(sourceAccount);
			creditTransaction.setSourceAccountNumber(destinationAccount);
			creditTransaction.setTransactionAmount(exchangeResponseDto.getConvertedAmount());
			creditTransaction.setTransactionDate(LocalDateTime.now());
			creditTransaction.setTransactionType(Constant.CREDIT);
			creditTransaction.setAvailableBalance(destinationAccount.getBalance());
			creditTransaction.setStatus(Constant.TRANSACTION_STATUS_SUCCESS);
			transactionRepository.save(creditTransaction);
			
			log.debug("Successfully updated the credit account details");
			
		
		}
		
	}
	}
	
	/**
	 * 
	 * @author PriyaDharshini S.
	 * @since 2020-02-11. This method will get the transactions of the current user.
	 * @param accountNumber - accountNumber of the current logged in user.
	 * @return list of TransactionDetailsDto - it is having all the transaction details.
	 * @throws AccountnotFoundException it will throw the exception if the account is not
	 *                               registered.
	 * 
	 */
	public List<TransactionDetailsDto> getMyTransactions(Long accountNumber) throws AccountnotFoundException{
		Optional<Account> account = accountRepository.findById(accountNumber);
		if(!account.isPresent()) {
			log.debug("Exception occurred in AccountServiceImpl getMyTransactions method:"+Constant.ACCOUNT_NOT_FOUND);
			throw new AccountnotFoundException(Constant.ACCOUNT_NOT_FOUND);
		}
		List<Transaction> transactions = transactionRepository.findBySourceAccountNumber(account.get());
		if(transactions.isEmpty()) {
			log.debug("Exception occurred in AccountServiceImpl getMyTransactions method:"+Constant.TRANSACTION_NOT_FOUND);
			throw new AccountnotFoundException(Constant.ACCOUNT_NOT_FOUND);
		}
		List<TransactionDetailsDto> detailsDtos = new ArrayList<>();
		log.info("Entering into AccountServiceImpl getMyTransactions method:getting transaction details");
		transactions.forEach(mytransaction -> {
			TransactionDetailsDto transactionDetailsDto = new TransactionDetailsDto();
			transactionDetailsDto.setAvailableBalance(mytransaction.getAvailableBalance());
			transactionDetailsDto.setCurrency(mytransaction.getCurrency().getCurrencyName());
			transactionDetailsDto.setDestinationAccountNumber(mytransaction.getDestinationAccountNumber().getAccountNumber());
			transactionDetailsDto.setStatus(mytransaction.getStatus());
			transactionDetailsDto.setTransactionAmount(mytransaction.getTransactionAmount());
			transactionDetailsDto.setTransactionDate(mytransaction.getTransactionDate());
			transactionDetailsDto.setTransactionType(mytransaction.getTransactionType());
			detailsDtos.add(transactionDetailsDto);
		});
		return detailsDtos;
	}

}
