package com.accesodatos.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;
import com.accesodatos.entity.CartItem;
import com.accesodatos.entity.Product;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.CartItemMapper;
import com.accesodatos.repository.CartItemRepository;
import com.accesodatos.repository.ProductRepository;
import com.accesodatos.repository.UserEntityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService{

	@Autowired private CartItemRepository cartItemRepository;

	@Autowired private UserEntityServiceImpl userServiceImpl;
	@Autowired private CartItemMapper cartItemMapper;
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private ProductRepository productRepository;
	@Autowired private ProductServiceImpl productServiceImpl;
	
	private static final String PRODUCT_NOT_FOUND = "User with id %d was not found.";
	
	@Override
	public List<CartItemResponseDto> getAllCartItems() {
		List<CartItem> cart = cartItemRepository.findAll();
		return cart.stream().map(cartItemMapper::toCartItemResponseDto).collect(Collectors.toList());
	}
	
	@Override
	public Boolean addproductToCart(Long userId, CartItemRequestDto dto) {
	    UserEntity user = userServiceImpl.validateAndGetUser(userId);

	    Optional<CartItem> optionalItem = user.getBasket().stream()
	            .filter(cartItem -> cartItem.getProduct().getProductId().equals(dto.getProductId()))
	            .findFirst();

	    if (optionalItem.isPresent()) {
	        optionalItem.get().setCuantity(dto.getCuantity());
	    } else {
	    	
	    	Product product = productServiceImpl.validateAndGetProduct(dto.getProductId());

	        CartItem newItem = new CartItem();
	        newItem.setProduct(product);
	        newItem.setCuantity(dto.getCuantity());
	        newItem.setUser(user);
	        user.getBasket().add(newItem);
	    }

	    userEntityRepository.save(user);

	    return true;
	}

	@Override
	public Boolean deleteCartItem(Long userId, Long itemId) {
		UserEntity user = userServiceImpl.validateAndGetUser(userId);
		user.getBasket().removeIf( item -> item.getProduct().getProductId().equals(itemId));
		userEntityRepository.save(user);
		return true;
	}
	
	@Override
	public Boolean updateCartItem(Long userId, CartItemRequestDto dto) {
		UserEntity user = userServiceImpl.validateAndGetUser(userId);
	    CartItem itemToUpdate = user.getBasket().stream()
	            .filter(cartItem -> cartItem.getProduct().getProductId().equals(dto.getProductId()))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    String.format("Producto con ID %d no encontrado en el carrito del usuario ID %d", dto.getProductId(), userId)));
	    itemToUpdate.setCuantity(dto.getCuantity());
		user.getBasket().stream().map( cartItem -> cartItem.getProduct().getProductId() == dto.getProductId());
		userEntityRepository.save(user);
		return true;
	}

}
