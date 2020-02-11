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

import com.squad.forexpay.dto.LoginRequestDto;
import com.squad.forexpay.dto.LoginResponseDto;
import com.squad.forexpay.entity.User;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRepository userRepository;
	
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
	public void testAuthenticateUser() throws UserNotFoundException {
		Mockito.when(userRepository.findByMobileAndPassword(1L, "sri")).thenReturn(Optional.of(user));
		LoginResponseDto actual = userServiceImpl.authenticateUser(loginRequestDto);
		assertEquals(1, actual.getUserId());
	}

}
