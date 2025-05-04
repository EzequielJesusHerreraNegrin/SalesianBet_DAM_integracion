package com.accesodatos.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.accesodatos.dto.cartitem.CartItemRequestDto;
import com.accesodatos.dto.cartitem.CartItemResponseDto;
import com.accesodatos.entity.CartItem;
import com.accesodatos.entity.Product;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.NotEnoughPointsException;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.CartItemMapper;
import com.accesodatos.repository.CartItemRepository;
import com.accesodatos.repository.UserEntityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService{

	@Autowired private CartItemRepository cartItemRepository;
	@Autowired private CartItemMapper cartItemMapper;
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private ProductServiceImpl productServiceImpl;
	
	private static final String CARTITEM_NOT_FOUND = "CartItem with id %d was not found.";
	private static final String USER_NOT_FOUND = "User with id %d was not found.";
	private static final String USER_FAIL_PURCHASE = "User purchase fail due to basket total price is higher than the points he owns.";
	
	
	@Override
	public List<CartItemResponseDto> getAllCartItems() {
		List<CartItem> cart = cartItemRepository.findAll();
		return cart.stream().map(cartItemMapper::toCartItemResponseDto).collect(Collectors.toList());
	}
	@Override
	public Boolean addproductToCart(Long cartItemtId, Long userId) {
		CartItem item = validateAndGetCartItem(cartItemtId);
		
		UserEntity user = validateAndGetUser(userId);
		
		user.getBasket().add(item);
		
		return true;
	}
	@Override
	@Transactional
	public Boolean buyCartItems(List<CartItemRequestDto> cartItems, Long userId) {
		UserEntity user = validateAndGetUser(userId);
		List<Product> purchase = new ArrayList<>();
		int cartPrice = 0;
		
		for (CartItemRequestDto item : cartItems) {
			Product product = productServiceImpl.validateAndGetProduct(item.getProductId());
			cartPrice = cartPrice + product.getPrice() * item.getCuantity();
			purchase.add(product);
		}
		
		if (user.getPoints() >= cartPrice) {
			purchase.forEach(user::buyProduct);
		} else {
			throw new NotEnoughPointsException(USER_FAIL_PURCHASE);
		}
		user.getBasket().clear();
		return true;
	}
	@Override
	public Boolean deleteCartItem(Long cartitemId) {
		cartItemRepository.deleteById(cartitemId);
		return true;
	}
	@Override
	public Boolean updateCartItem(Long userId, CartItemRequestDto dto) {
		UserEntity user =  validateAndGetUser(userId);
		CartItem item =  cartItemMapper.toCartItem(dto);
		user.getBasket().stream().map( cartItem -> cartItem.getProduct().getProductId() == dto.getProductId());
		item.setCuantity(dto.getCuantity());
		cartItemRepository.save(item);
		return true;
	}
	
	private CartItem validateAndGetCartItem(Long id) {
		return cartItemRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException(String.format(CARTITEM_NOT_FOUND, id)));
	}
	
	private UserEntity validateAndGetUser(Long id) {
		return userEntityRepository.findById(id).
				orElseThrow(() -> new ResourceNotFoundException(String.format(USER_NOT_FOUND, id)));
	}
	
}
