package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;
import com.accesodatos.service.impl.CartItemServiceImpl;

@RestController
@RequestMapping("api/v1")
public class CartItemController {

	private static final String CARTITEM_RESOURCE = "/cartItems";
	private static final String CARTITEM_PRODUCT_ID = CARTITEM_RESOURCE + "/product/{userId}";
	private static final String CARTITEM_USER_ID_PRODUCT_ID = CARTITEM_RESOURCE + "/{userId}/product/{productId}";
	
	@Autowired CartItemServiceImpl cartItemServiceImpl;
	
	@GetMapping(value = CARTITEM_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<CartItemResponseDto>>> getAllCartItems() {
		List<CartItemResponseDto> items = cartItemServiceImpl.getAllCartItems();
		
		ApiResponseDto<List<CartItemResponseDto>> response = new ApiResponseDto<List<CartItemResponseDto>>("All items were fetched successfuly", HttpStatus.OK.value(), items);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = CARTITEM_PRODUCT_ID, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> addProductToCart(
			@RequestBody CartItemRequestDto dto,@PathVariable Long userId) {
		
		Boolean items = cartItemServiceImpl.addproductToCart(userId, dto);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("Item/s added successfuly", HttpStatus.OK.value(), items);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping(value = CARTITEM_PRODUCT_ID, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> updateCartItem(@PathVariable Long userId, @RequestBody CartItemRequestDto dto) {
		Boolean updated = cartItemServiceImpl.updateCartItem(userId, dto);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("Item/s added successfuly", HttpStatus.OK.value(), updated);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping(value = CARTITEM_USER_ID_PRODUCT_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> deleteCartItem(@PathVariable Long userId, @PathVariable Long productId) {
		Boolean updated = cartItemServiceImpl.deleteCartItem(userId, productId);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("Item/s added successfuly", HttpStatus.OK.value(), updated);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
