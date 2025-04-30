package com.accesodatos.dto.cartitem;

import com.accesodatos.dto.product.ProductResponseDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemRequestDto {

	private ProductResponseDto product;
	
	@NotBlank(message = "You should get at least 1 product.")
	private int cuantity;
}
