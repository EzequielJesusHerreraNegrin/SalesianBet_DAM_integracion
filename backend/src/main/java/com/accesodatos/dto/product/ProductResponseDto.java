package com.accesodatos.dto.product;

import java.util.List;

import com.accesodatos.dto.purchase.PurchaseResponseDto;
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
	private List<PurchaseResponseDto> buys;
	private int price;
}