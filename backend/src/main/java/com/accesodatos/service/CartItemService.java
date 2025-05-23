package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;

public interface CartItemService {

	List<CartItemResponseDto> getAllCartItems();
	List<CartItemResponseDto> getAllCartItemsByUserId(Long userId);
	CartItemResponseDto addproductToCart (CartItemRequestDto dto);
	Boolean deleteCartItem (Long userId, Long itemId);
	CartItemResponseDto updateCartItem (CartItemRequestDto dto);
}
