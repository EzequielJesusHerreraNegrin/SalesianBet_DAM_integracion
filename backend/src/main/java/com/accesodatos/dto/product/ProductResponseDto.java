package com.accesodatos.dto.product;

import java.util.Set;

import com.accesodatos.dto.userentity.UserEntityResponseDto;
import com.accesodatos.entity.enums.ProductState;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductResponseDto {

	private String productId;
	private String productName;
	private String productImage;
	private ProductState state;
	private int price;
}