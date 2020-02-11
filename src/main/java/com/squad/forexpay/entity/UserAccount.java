package com.squad.forexpay.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserAccount {
	@Id
	private Integer userAccountId;
	@ManyToOne
	@JoinColumn(name = "account_number")
	private Account account;
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

}
