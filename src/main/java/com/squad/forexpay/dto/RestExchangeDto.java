package com.squad.forexpay.dto;

import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestExchangeDto {
	
	private HashMap<String, Double> rates;

}
