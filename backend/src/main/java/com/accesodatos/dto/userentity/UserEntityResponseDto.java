package com.accesodatos.dto.userentity;

import lombok.Data;

@Data
public class UserEntityResponseDto {
	
	private Long userId;
	private String userName;
	private String email;
	private String dni;
	private int points;
	private String Country;
}
