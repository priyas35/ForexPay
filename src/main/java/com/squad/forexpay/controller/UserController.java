package com.squad.forexpay.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.LoginRequestDto;
import com.squad.forexpay.dto.LoginResponseDto;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@CrossOrigin
@Slf4j
public class UserController {
	
	@Autowired
	UserService userService;

	/**
	 * 
	 * @author PriyaDharshini S.
	 * @since 2020-02-11. This method will authenticate the user.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message,statusCode,role of the user
	 *         and userDetails.
	 * @throws UserNotFoundException it will throw the exception if the user is not
	 *                               registered.
	 * 
	 */
	@PostMapping
	public ResponseEntity<LoginResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto)
			throws UserNotFoundException {
		LoginResponseDto loginResponseDto = userService.authenticateUser(loginRequestDto);
		log.info("Entering into UserController authenticateUser metod: calling UserService");
		loginResponseDto.setStatusMessage(Constant.AUTHENTICATION_SUCCESSFUL);
		loginResponseDto.setStatusCode(Constant.AUTHENTICATION_SUCCESSFUL_CODE);
		return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
	}
	
	


}
