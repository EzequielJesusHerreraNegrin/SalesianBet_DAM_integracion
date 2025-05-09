package com.accesodatos.dto.auth;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthRegisterRequestDto {

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "The email shound end with the domain '@gmail.com'.")
	private String email;
	
	@NotBlank
	@Column(length = 50)
	private String userName;
	
	@NotBlank
	@Size(min = 9, max = 9,message = "The DNI should have 8 numbers and 1 letter of the alfabet.")
	private String dni;
	
	@Pattern(regexp = "^[a-zA-ZáéíóúüñÑ\\s]+$", message = "The country only accepts letters and spaces.")
	private String country;
	
	@NotBlank
	@Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
	private String password;
}
