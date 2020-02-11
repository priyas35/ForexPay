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

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.LoginRequestDto;
import com.squad.forexpay.dto.LoginResponseDto;
import com.squad.forexpay.entity.User;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;
	
	LoginResponseDto loginResponseDto = new LoginResponseDto();
	LoginRequestDto loginRequestDto = new LoginRequestDto();
    User user = new User();
     
	@Before
	public void setUp() {
		user.setUserId(1);
		user.setPassword("sri");
		user.setMobile(1L);
		loginRequestDto.setMobile(1L);
		loginRequestDto.setPassword("sri");
		loginResponseDto.setUserId(1);
		loginResponseDto.setUserName("sri");
	}
	
	@Test
	public void testAuthenticateEmployee() throws UserNotFoundException {
		loginResponseDto.setStatusMessage(Constant.AUTHENTICATION_SUCCESSFUL);
		loginResponseDto.setStatusCode(Constant.AUTHENTICATION_SUCCESSFUL_CODE);
		Mockito.when(userService.authenticateUser(loginRequestDto)).thenReturn(loginResponseDto);
		ResponseEntity<LoginResponseDto> result = userController.authenticateUser(loginRequestDto);
		assertEquals(HttpStatus.OK, result.getStatusCode());

	}

	
	

}
