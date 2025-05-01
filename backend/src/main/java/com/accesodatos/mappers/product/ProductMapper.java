package com.accesodatos.mappers.product;

import java.util.ArrayList;
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
	
//	default List<Product> toProducts(List<CartItemRequestDto> dtos) {
//        List<Product> products = new ArrayList<>();
//
//        for (CartItemRequestDto dto : dtos) {
//            int quantity = dto.getCuantity() > 0 ? dto.getCuantity() : 1;
//
//            for (int i = 0; i < quantity; i++) {
//                //products.add();
//            }
//        }
//        return products;
//    }
	
}
