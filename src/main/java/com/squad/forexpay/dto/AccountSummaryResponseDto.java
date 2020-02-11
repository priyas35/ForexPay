package com.squad.forexpay.dto;

import lombok.Data;

@Data
public class AccountSummaryResponseDto {

	private Double minimumBalance;
	private String currencyType;
	private Long accountNumber;
	private String bankName;
	private String branchName;
	private Double balance;

}
