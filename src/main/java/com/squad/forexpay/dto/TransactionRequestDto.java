package com.squad.forexpay.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequestDto {

	@NotNull(message="userId cannot be null")
	private Integer userId;
	@NotNull(message="destinationAccountNumber cannot be null")
	private Long destinationAccountNumber;
	@NotNull(message="transactionAmount cannot be null")
	private Double transactionAmount;
}
