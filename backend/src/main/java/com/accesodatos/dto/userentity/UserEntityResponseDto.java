package com.accesodatos.dto.userentity;

import java.util.List;

import com.accesodatos.dto.cartitem.CartItemUserResponseDto;

import lombok.Data;

@Data
public class UserEntityResponseDto {
	
	private Long userId;
	private String userName;
	private String email;
	private String dni;
	private int points;
	private String country;
	private List<CartItemUserResponseDto> basket;
}
