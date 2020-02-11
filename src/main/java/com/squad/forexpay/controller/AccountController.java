package com.squad.forexpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.service.AccountService;

@RestController
@RequestMapping("/accounts")
@CrossOrigin
public class AccountController {

	@Autowired
	AccountService accountService;

	@GetMapping("/{userId}")
	public ResponseEntity<AccountSummaryResponseDto> getAccountSummary(@PathVariable("userId") Integer userId)
			throws UserNotFoundException, AccountnotFoundException {
		return ResponseEntity.ok().body(accountService.getAccountSummary(userId));
	}

}
