package com.accesodatos.dto.userentity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserEntityResponseDto {

	private Long userId;
	private String userName;
	private String password;
	private String email;
	private String dni;
	private int points; 
	private String country;
	// private Set<RoleResponseDto> roles;
	// private List<BetResponseDto> bets;
	// private Set<ProductResponseDto> products;
}
