package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;
import com.accesodatos.service.CartItemServiceImpl;
import com.accesodatos.service.ProductServiceImpl;

@RestController
@RequestMapping("api/v1")
public class CartItemController {

	private static final String CARTITEM_RESOURCE = "/cartItems";
	private static final String CARTITEM_STATE = CARTITEM_RESOURCE + "/state";
	private static final String CARTITEM_PATH_ID = CARTITEM_RESOURCE + "/{productId}";
	private static final String CARTITEM_ID_STATE = CARTITEM_PATH_ID +"/state";
	
	@Autowired CartItemServiceImpl cartItemServiceImpl;
	
	@GetMapping(value = CARTITEM_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<CartItemResponseDto>>> getAllCartItems() {
		List<CartItemResponseDto> items = cartItemServiceImpl.getAllCartItems();
		
		ApiResponseDto<List<CartItemResponseDto>> response = new ApiResponseDto<List<CartItemResponseDto>>("All items were fetched successfuly", HttpStatus.OK.value(), items);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = CARTITEM_RESOURCE+ "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> addProductToCart(
			@RequestBody CartItemRequestDto dto,@PathVariable Long userId) {
		
		Boolean items = cartItemServiceImpl.addproductToCart(userId, dto);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>("All items were fetched successfuly", HttpStatus.OK.value(), items);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
