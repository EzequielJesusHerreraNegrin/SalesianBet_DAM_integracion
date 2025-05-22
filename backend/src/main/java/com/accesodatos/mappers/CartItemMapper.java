package com.accesodatos.mappers;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;
import com.accesodatos.entity.CartItem;
import com.accesodatos.repository.UserEntityRepository;
import com.accesodatos.service.impl.UserEntityServiceImpl;

@Mapper(componentModel = "spring")
public interface CartItemMapper {
	
	public static final UserEntityRepository entityRepository = null;

	CartItemResponseDto toCartItemResponseDto(CartItem cartItem);

	@Mapping(target = "cartId", ignore = true)
	@Mapping(target = "user", expression = "java(userService.validateAndGetUser(dto.getUserId()))")
	@Mapping(target = "product", ignore = true)
	CartItem toCartItem(CartItemRequestDto dto, @Context UserEntityServiceImpl userService);
}
