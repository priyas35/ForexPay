package com.squad.forexpay.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.LoginRequestDto;
import com.squad.forexpay.dto.LoginResponseDto;
import com.squad.forexpay.entity.User;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.repository.TransactionRepository;
import com.squad.forexpay.repository.UserAccountRepository;
import com.squad.forexpay.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserAccountRepository userAccountRepository;

	@Autowired
	TransactionRepository transactionRepository;

	/**
	 * @author PriyaDharshini S.
	 * @since 2020-02-11. This method will authenticate the user.
	 * @param loginDto - details of the user login
	 * @return LoginResponseDto which has status message,statusCode,role of the user
	 *         and userDetails.
	 * @throws UserNotFoundException it will throw the exception if the user is not
	 *                               registered.
	 * 
	 */
	@Override
	public LoginResponseDto authenticateUser(LoginRequestDto loginRequestDto) throws UserNotFoundException {
		Optional<User> user = userRepository.findByMobileAndPassword(loginRequestDto.getMobile(),
				loginRequestDto.getPassword());
		if (!user.isPresent()) {
			log.error("Entering into UserServiceImpl authenticateUser method:" + Constant.USER_NOT_FOUND);
			throw new UserNotFoundException(Constant.USER_NOT_FOUND);
		} else {
			LoginResponseDto loginResponseDto = new LoginResponseDto();
			loginResponseDto.setUserId(user.get().getUserId());
			loginResponseDto.setUserName(user.get().getUserName());
			log.info("Authentication Successful");
			return loginResponseDto;
		}
	}


	  }
	

