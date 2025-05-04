package com.accesodatos.dto.userentity;

import java.util.List;

import com.accesodatos.dto.cartitem.CartItemUserResponseDto;
import com.accesodatos.dto.purchase.PurchaseResponseDto;

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
	private List<PurchaseResponseDto> purchases;
}
