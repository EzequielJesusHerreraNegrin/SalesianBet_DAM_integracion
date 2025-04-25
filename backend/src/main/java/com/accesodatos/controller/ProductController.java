package com.accesodatos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accesodatos.dto.api.ApiResponseDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.service.ProductServiceImpl;



@RestController
@RequestMapping("api/v1")
public class ProductController {

	private static final String PRODUCT_RESOURCE = "/productos";
	private static final String PRODUCT_PATH_ID = PRODUCT_RESOURCE + "/{productoId}";
	
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
}
