package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;

public interface CartItemService {

	List<CartItemResponseDto> getAllCartItems();
	Boolean addproductToCart (Long userId, CartItemRequestDto dto);
	Boolean buyCartItems ( Long userId);
	Boolean deleteCartItem (Long cartitemId);
	Boolean updateCartItem (Long userId, CartItemRequestDto dto);
}
