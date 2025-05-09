package com.accesodatos.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthLoginRequestDto {

	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
}
