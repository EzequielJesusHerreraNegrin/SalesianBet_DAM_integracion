package com.accesodatos.service;

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
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.CartItemMapper;
import com.accesodatos.mappers.product.ProductMapper;
import com.accesodatos.repository.CartItemRepository;
import com.accesodatos.repository.UserEntityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService{

	@Autowired private CartItemRepository cartItemRepository;
	@Autowired private CartItemMapper cartItemMapper;
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private ProductMapper productMapper;
//	@Autowired private ;
	
	private final String CARTITEM_NOT_FOUND = "CartItem with id %d was not found.";
	private final String USER_NOT_FOUND = "User with id %d was not found.";
	
	
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
		int cartPrice = 0;
		
		for (CartItemRequestDto item : cartItems) {
			int cuantity = item.getCuantity() > 1 ? item.getCuantity() : null;
			cartPrice = cartPrice + item.getProduct().getPrice() * cuantity;
		}
		
		if (user.getPoints() > cartPrice) {
			List<Product> products = productMapper.toProducts(cartItems);
			products.forEach((product) -> user.buyProduct(product));
			
		}
		
		return null;
	}
	@Override
	public Boolean deleteCartItem(Long cartitemId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean updateCartItem(Long cartitemId, CartItemRequestDto dto) {
		// TODO Auto-generated method stub
		return null;
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
