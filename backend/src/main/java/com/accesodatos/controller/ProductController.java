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
import com.accesodatos.dto.product.ProductRequestDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.service.ProductServiceImpl;



@RestController
@RequestMapping("api/v1")
public class ProductController {

	private static final String PRODUCT_RESOURCE = "/products";
	private static final String PRODUCT_PATH_ID = PRODUCT_RESOURCE + "/{productId}";
	
	@Autowired ProductServiceImpl productServiceImpl;
	
	@GetMapping(value = PRODUCT_RESOURCE+"/ping", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ping(){
		return new ResponseEntity<>("Pong ....producto", HttpStatus.OK);
	}
	
	@GetMapping(value = PRODUCT_RESOURCE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<List<ProductResponseDto>>> getAllProducts(){
		List<ProductResponseDto> products = productServiceImpl.getAllProducts();
		
		ApiResponseDto<List<ProductResponseDto>> response = 
				new ApiResponseDto<List<ProductResponseDto>>("All products fetched successfuly",
						HttpStatus.OK.value(), products);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping(value = PRODUCT_RESOURCE, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> createProduct(@RequestBody ProductRequestDto dto) {
		Boolean created = productServiceImpl.createProduct(dto);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>(
				"Product created successfuly.", HttpStatus.CREATED.value(), created);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping(value = PRODUCT_PATH_ID, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> updateProduct(@PathVariable Long productId,
			@RequestBody ProductRequestDto dto) {
		Boolean updated = productServiceImpl.updateProduct(dto, productId);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>(
				"Product updated successfuly.", HttpStatus.CREATED.value(), updated);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = PRODUCT_PATH_ID, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ApiResponseDto<Boolean>> deleteProduct(@PathVariable Long productId) {
		Boolean deleted = productServiceImpl.deleteProduct(productId);
		
		ApiResponseDto<Boolean> response = new ApiResponseDto<Boolean>(
				"Product deleted successfuly.", HttpStatus.OK.value(), deleted);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
