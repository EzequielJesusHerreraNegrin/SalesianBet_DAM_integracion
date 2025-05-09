package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.product.ProductRequestDto;
import com.accesodatos.dto.product.ProductResponseDto;

public interface ProductService {

	List<ProductResponseDto> getAllProducts();
	ProductResponseDto getProductById(Long id);
	Boolean createProduct(ProductRequestDto dto);
	Boolean updateProduct(ProductRequestDto dto, Long id);
	Boolean deleteProduct(Long id);
	Boolean manageProductSate(Long productId, String state);
}
