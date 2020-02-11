package com.squad.forexpay.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
	@NotNull(message = "mobilenumber is required")
	private Long mobile;
	@NotNull(message = "password is required")
	private String password;

}
