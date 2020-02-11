package com.squad.forexpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.forexpay.dto.ResponseDto;
import com.squad.forexpay.dto.TransactionRequestDto;
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
	 * 
	 * @param transactionRequestDto
	 * @return
	 */
	@PostMapping("/transactions")
	public ResponseEntity<ResponseDto> transferCurrency(@RequestBody TransactionRequestDto transactionRequestDto){
	log.info("Entering into transferCurrency of AccountController");
	ResponseDto responseDto=accountService.transferCurrency(transactionRequestDto);
	responseDto.setStatusCode(HttpStatus.OK.value());
	responseDto.setStatusMessage("success");
	return new ResponseEntity<>(responseDto,HttpStatus.OK);	
	}
}
