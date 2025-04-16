package com.accesodatos.dto.userentity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserEntityRegisterRequestDto {
	
	@NotBlank(message = "Debe introducir un nombre válido.")
	private String userName;
	
	@NotBlank(message = "Debe introducir una clave válida.")
	private String password;
	
	@NotBlank(message = "Debe proporcionar un email al que asociar su cuenta.")
	private String email;
	
	@NotBlank(message = "Debe justificar su identidad física.")
	private String dni;
	
	private String Country;
}
