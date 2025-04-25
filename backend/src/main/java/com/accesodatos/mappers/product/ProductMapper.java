package com.accesodatos.mappers.product;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.product.ProductRequestDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	@Mapping(target = "productImage", ignore = true)
	ProductResponseDto toProductResponseDto(Product product);
	
	@Mapping(target = "productImage", ignore = true)
	@Mapping(target = "users", ignore = true)
	@Mapping(target = "productId", ignore = true)
	Product toProduct(ProductRequestDto dto);
}
