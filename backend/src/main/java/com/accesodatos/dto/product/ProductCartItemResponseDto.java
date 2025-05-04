package com.accesodatos.dto.product;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCartItemResponseDto {

	private String productId;
	private String productName;
	private String productImage;
	private int price;
}
