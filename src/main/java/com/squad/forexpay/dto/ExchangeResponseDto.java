package com.squad.forexpay.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeResponseDto {
	
	private Double convertedAmount;
	private Double serviceTax;
	private Double totalAmount;

}
