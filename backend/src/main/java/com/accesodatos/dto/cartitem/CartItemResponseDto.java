package com.accesodatos.dto.cartitem;

import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.dto.userentity.UserEntityResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemResponseDto {

	private Long cartId;
	private UserEntityResponseDto user;
	private ProductResponseDto product;
	private int cuantity;
}
