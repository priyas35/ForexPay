package com.squad.forexpay.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Setter
@Getter
@SequenceGenerator(name = "transactionId", initialValue = 10001, allocationSize = 1)
@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionId")
	private Long transactionId;
	@ManyToOne
	@JoinColumn(name = "source_account_number")	
	private Account sourceAccountNumber;
	@ManyToOne
	@JoinColumn(name = "destination_account_number")
	private Account destinationAccountNumber;
	private Double transactionAmount;
	private Double availableBalance;
	private String transactionType;
	private LocalDateTime transactionDate;
	@ManyToOne
	@JoinColumn(name = "currency_code")
	private Currency currency;
	private String status;

}
