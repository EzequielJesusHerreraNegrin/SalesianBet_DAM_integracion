package com.accesodatos.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.product.ProductRequestDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.entity.Product;
import com.accesodatos.entity.enums.ProductState;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.product.ProductMapper;
import com.accesodatos.repository.ProductRepository;
import com.accesodatos.repository.UserEntityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService{

	@Autowired ProductRepository productRepository;
	@Autowired UserEntityRepository userRepository;
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
						"The product with id %d was not found.", id)));
		
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
		Product product = productRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException(String.format(
						"The product with id %d was not found.", id)));
		
		product.setPrice(dto.getPrice());
		product.setProductImage(dto.getImageImage());
		product.setProductName(dto.getProductName());
		product.setState(dto.getState());
		
		productRepository.save(product);
		
		return true;
	}

	@Override
	public Boolean deleteProduct(Long id) {
		Product product = productRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException(String.format(
						"The product with id %d was not found.", id)));
		
			productRepository.deleteById(id);			
		
		return true;
	}

	@Override
	public Boolean manageProductSate(Long productId, String state) {
		Product product = productRepository.findById(productId).
				orElseThrow(() -> new ResourceNotFoundException(String.format(
						"The product with id %d was not found.", productId)));
		
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

	
//	public Boolean addProductToUserCart(Long productId, Long userId) {
//		UserEntity user = userRepository.findById(userId).
//				orElseThrow(() -> new ResourceNotFoundException(String.format(
//						"The product with id %d was not found.", userId)));
//		Product product = productRepository.findById(productId).
//				orElseThrow(() -> new ResourceNotFoundException(String.format(
//						"The product with id %d was not found.", productId)));
//		CartItem cartItem = new CartItem();
//		cartItem.setProduct(product);
//		cartItem.setUser(user);
//		user.getBasket().add(cartItem);
//		userRepository.save(user);
//		
//		return true;
//	}

}
