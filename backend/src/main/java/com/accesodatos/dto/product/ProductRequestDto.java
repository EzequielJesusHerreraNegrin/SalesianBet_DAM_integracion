package com.accesodatos.dto.product;

import com.accesodatos.entity.enums.ProductState;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDto {

	private String productName;
	private String imageImage;
	private ProductState state;
	private int price;
	private Long userId;
}
