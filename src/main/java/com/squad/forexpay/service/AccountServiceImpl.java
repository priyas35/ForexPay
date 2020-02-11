package com.squad.forexpay.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.squad.forexpay.constant.Constant;
import com.squad.forexpay.dto.AccountSummaryResponseDto;
import com.squad.forexpay.entity.User;
import com.squad.forexpay.entity.UserAccount;
import com.squad.forexpay.exception.AccountnotFoundException;
import com.squad.forexpay.exception.UserNotFoundException;
import com.squad.forexpay.repository.UserAccountRepository;
import com.squad.forexpay.repository.UserRepository;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	UserAccountRepository userAccountRepository;
	
	@Autowired
	UserRepository userRepository;

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

}
