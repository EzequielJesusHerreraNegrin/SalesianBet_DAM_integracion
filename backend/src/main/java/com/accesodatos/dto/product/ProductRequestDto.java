package com.accesodatos.dto.product;

import java.util.Set;

import com.accesodatos.dto.userentity.UserEntityResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequestDto {

	private String productName;
	private String imageImage;
	private int price;
	private Long userId;
}
