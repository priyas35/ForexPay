package com.squad.forexpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.dto.ResponseDto;
import com.squad.forexpay.dto.TransactionDetailsDto;
import com.squad.forexpay.dto.TransactionRequestDto;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.service.AccountService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
@Slf4j
public class AccountController {

	@Autowired
	AccountService accountService;

	/**
	 * This method is used to do fund transfer from one account to another account
	 * 
	 * @author chethana
	 * @param transactionRequestDto- Intakes transaction details
	 * @return ResponseDto
	 * @throws UserNotFoundException-   thrown when the userId is invalid
	 * @throws AccountnotFoundException - thrown when the source/destination account
	 *                                  is invalid
	 */
	@PostMapping("/transactions")
	public ResponseEntity<ResponseDto> transferCurrency(@RequestBody TransactionRequestDto transactionRequestDto)
			throws UserNotFoundException, AccountnotFoundException {
		log.info("Entering into transferCurrency of AccountController");
		ResponseDto responseDto = accountService.transferCurrency(transactionRequestDto);
		responseDto.setStatusCode(HttpStatus.OK.value());
		responseDto.setStatusMessage("success");
		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

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
	@GetMapping("/{userId}")
	public ResponseEntity<AccountSummaryResponseDto> getAccountSummary(@PathVariable("userId") Integer userId)
			throws UserNotFoundException, AccountnotFoundException {
		return ResponseEntity.ok().body(accountService.getAccountSummary(userId));
	}

	/**
	 * 
	 * @author PriyaDharshini S.
	 * @since 2020-02-11. This method will get the transactions of the current user.
	 * @param accountNumber - accountNumber of the current logged in user.
	 * @return list of TransactionDetailsDto - it is having all the transaction
	 *         details.
	 * @throws AccountnotFoundException it will throw the exception if the account
	 *                                  is not registered.
	 * 
	 */
	@GetMapping("/{accountNumber}/transactions")
	public ResponseEntity<List<TransactionDetailsDto>> getMyTransaction(@PathVariable Long accountNumber)
			throws AccountnotFoundException {
		if (accountNumber == null) {
			throw new AccountnotFoundException(Constant.ACCOUNT_NOT_FOUND);
		} else {
			return new ResponseEntity<>(accountService.getMyTransactions(accountNumber), HttpStatus.OK);
		}
	}
	
}
