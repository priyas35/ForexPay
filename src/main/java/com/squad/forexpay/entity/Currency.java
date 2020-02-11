package com.squad.forexpay.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Currency {
	
	@Id
	private String code;
	private String currencyName;

}
