package com.accesodatos.dto.cartitem;

import com.accesodatos.dto.product.ProductCartItemResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemUserResponseDto {

	private ProductCartItemResponseDto product;
	private int cuantity;
}
