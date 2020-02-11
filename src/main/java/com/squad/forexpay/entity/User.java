package com.squad.forexpay.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {
	@Id
	private Integer userId;
	private String userName;
	private String password;
	private Long mobile;
	private String email;
	private String address;

}
