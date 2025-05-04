package com.accesodatos.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;
import com.accesodatos.entity.CartItem;


@Mapper(componentModel = "spring")
public interface CartItemMapper {

	//@Mapping(target = "productName", source = "product.productName")
	CartItemResponseDto toCartItemResponseDto (CartItem cartItem);
	
	@Mapping(target = "cartId", ignore = true)
	@Mapping(target = "user", ignore = true)
	@Mapping(target = "product", ignore = true)
	CartItem toCartItem(CartItemRequestDto dto);
	
	
}
