package com.accesodatos.dto.product;

import java.util.Set;

import com.accesodatos.dto.userentity.UserEntityResponseDto;

import lombok.Data;

@Data
public class ProductResponseDto {

	private String productId;
	private String productName;
	private String imageImage;
	private int price;
	private Set<UserEntityResponseDto> user;
}