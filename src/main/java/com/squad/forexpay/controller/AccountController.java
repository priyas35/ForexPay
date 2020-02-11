package com.squad.forexpay.controller;

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

import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.dto.ResponseDto;
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
	 * @throws UserNotFoundException- thrown when the userId is invalid
	 * @throws AccountnotFoundException - thrown when the source/destination account is invalid
	 */
	@PostMapping("/transactions")
	public ResponseEntity<ResponseDto> transferCurrency(@RequestBody TransactionRequestDto transactionRequestDto) throws UserNotFoundException, AccountnotFoundException{
	log.info("Entering into transferCurrency of AccountController");
	ResponseDto responseDto=accountService.transferCurrency(transactionRequestDto);
	responseDto.setStatusCode(HttpStatus.OK.value());
	responseDto.setStatusMessage("success");
	return new ResponseEntity<>(responseDto,HttpStatus.OK);	
	}

	@GetMapping("/{userId}")
	public ResponseEntity<AccountSummaryResponseDto> getAccountSummary(@PathVariable("userId") Integer userId)
			throws UserNotFoundException, AccountnotFoundException {
		return ResponseEntity.ok().body(accountService.getAccountSummary(userId));
	}

}
