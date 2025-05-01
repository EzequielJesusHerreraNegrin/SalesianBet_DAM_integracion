package com.accesodatos.mappers.product;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.product.ProductRequestDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductResponseDto toProductResponseDto(Product product);
	
	@Mapping(target = "productImage", ignore = true)
	@Mapping(target = "users", ignore = true)
	@Mapping(target = "state", ignore = true)	
	@Mapping(target = "productId", ignore = true)
	Product toProduct(ProductRequestDto dto);
	
	@Mapping(target = "cuantity", ignore = true)
	List<Product> toProducts(List<CartItemRequestDto> dtos);
}
