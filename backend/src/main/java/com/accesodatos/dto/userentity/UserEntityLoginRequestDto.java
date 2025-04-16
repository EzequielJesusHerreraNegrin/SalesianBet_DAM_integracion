package com.accesodatos.dto.userentity;

import lombok.Data;

@Data
public class UserEntityLoginRequestDto {
	
	private String password;
	private String email;
}
