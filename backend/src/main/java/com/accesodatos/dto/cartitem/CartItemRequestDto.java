package com.accesodatos.dto.cartitem;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemRequestDto {

	private Long userId;
	
	private Long productId;
	
	private int cuantity;
}
