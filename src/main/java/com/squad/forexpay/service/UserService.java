package com.squad.forexpay.service;

import com.squad.forexpay.dto.LoginRequestDto;
import com.squad.forexpay.dto.LoginResponseDto;
import com.squad.forexpay.exception.UserNotFoundException;

public interface UserService {

	LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) throws UserNotFoundException;

}
