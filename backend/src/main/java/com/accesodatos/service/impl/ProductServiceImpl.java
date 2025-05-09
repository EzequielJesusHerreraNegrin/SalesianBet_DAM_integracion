package com.accesodatos.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.product.ProductRequestDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.entity.Product;
import com.accesodatos.entity.enums.ProductState;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.product.ProductMapper;
import com.accesodatos.repository.ProductRepository;
import com.accesodatos.repository.UserEntityRepository;
import com.accesodatos.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

	@Autowired ProductRepository productRepository;
	@Autowired UserEntityRepository userRepository;
	@Autowired ProductMapper productMapper;
	
	private final String PRODUCT_NOT_FOUND = "Product with id %d was not found.";
	
	public Product validateAndGetProduct(Long id) {
		return productRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException(String.format(PRODUCT_NOT_FOUND, id)));
	}	
	
	@Override
	public List<ProductResponseDto> getAllProducts() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        boolean isAdmin = authentication.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .anyMatch(role -> role.equals("ROLE_ADMIN"));
        List<Product> products = new ArrayList<>();
        if (isAdmin) {
        	products = productRepository.findAll();			
		} else {
			products = productRepository.findByState(ProductState.PUBLICO);						
		}
		
		return products.stream().map(productMapper::toProductResponseDto).collect(Collectors.toList());
	}

	@Override
	public ProductResponseDto getProductById(Long id) {
		Product product = validateAndGetProduct(id);
		
		return productMapper.toProductResponseDto(product);
	}

	@Override
	public Boolean createProduct(ProductRequestDto dto) {
		Product newProduct = new Product();
		
		newProduct.setPrice(dto.getPrice());
		newProduct.setProductImage(dto.getImageImage());
		newProduct.setProductName(dto.getProductName());
		newProduct.setState(dto.getState());
		
		productRepository.save(newProduct);
		return true;
	}

	@Override
	public Boolean updateProduct(ProductRequestDto dto, Long id) {
		Product product = validateAndGetProduct(id);
		
		product.setPrice(dto.getPrice());
		product.setProductImage(dto.getImageImage());
		product.setProductName(dto.getProductName());
		product.setState(dto.getState());
		
		productRepository.save(product);
		
		return true;
	}

	@Override
	public Boolean deleteProduct(Long id) {
		productRepository.deleteById(id);			
		return true;
	}

	@Override
	public Boolean manageProductSate(Long productId, String state) {
		Product product = validateAndGetProduct(productId);
		
		String dinamicState = ProductState.valueOf(state).toString();
		System.out.println("AÑAKSDÑLKAJSDÑLKJASDÑLKJAS :"+dinamicState);
		if ( product.getState() == ProductState.valueOf(state)) {
			throw new IllegalArgumentException(String.format("The product has already asigned the state %s.", dinamicState));
			
		} else {
			product.setState(ProductState.valueOf(state));
			productRepository.save(product);
		}
		
		return true;
	}
}



