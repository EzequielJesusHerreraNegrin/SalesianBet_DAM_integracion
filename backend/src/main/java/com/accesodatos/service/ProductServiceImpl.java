package com.accesodatos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.product.ProductRequestDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.entity.Product;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.product.ProductMapper;
import com.accesodatos.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

	@Autowired ProductRepository productRepository;
	@Autowired ProductMapper productMapper;
	
	@Override
	public List<ProductResponseDto> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(productMapper::toProductResponseDto).collect(Collectors.toList());
	}

	@Override
	public ProductResponseDto gestProductById(Long id) {
		Product product = productRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException(String.format(
						"The product with id %id was not found.", id)));
		
		return productMapper.toProductResponseDto(product);
	}

	@Override
	public Boolean createProduct(ProductRequestDto dto) {
		Product newProduct = new Product();
		
		newProduct.setPrice(dto.getPrice());
		newProduct.setProductImage(dto.getImageImage());
		newProduct.setProductName(dto.getProductName());
		
		productRepository.save(newProduct);
		return true;
	}

	@Override
	public Boolean updateProduct(ProductRequestDto dto, Long id) {
		Product product = productRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException(String.format(
						"The product with id %id was not found.", id)));
		
		product.setPrice(0);
		product.setProductImage(dto.getImageImage());
		product.setProductName(dto.getProductName());
		
		return null;
	}

	@Override
	public Boolean deleteProduct(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addProductToUser(Long productId, Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
