package com.accesodatos.dto.cartitem;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemRequestDto {

	private Long productId;
	
	private int cuantity;
}
