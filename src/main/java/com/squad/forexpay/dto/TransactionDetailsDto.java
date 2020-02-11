package com.squad.forexpay.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionDetailsDto {

	private Long destinationAccountNumber;
	private Double transactionAmount;
	private Double availableBalance;
	private String transactionType;
	private LocalDateTime transactionDate;
	private String currency;
	private String status;

}
