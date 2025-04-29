package com.accesodatos.dto.match;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchResultRequestDto {

	@NotBlank(message = "Result must be not blank")
	@NotNull(message = "Result cannot be null")
	private String result;
}
