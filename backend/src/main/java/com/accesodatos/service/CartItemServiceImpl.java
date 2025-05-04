package com.accesodatos.service;

import java.time.LocalDateTime;
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
import com.accesodatos.entity.Purchase;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.NotEnoughPointsException;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.mappers.CartItemMapper;
import com.accesodatos.repository.CartItemRepository;
import com.accesodatos.repository.ProductRepository;
import com.accesodatos.repository.PurchaseRespository;
import com.accesodatos.repository.UserEntityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartItemServiceImpl implements CartItemService{

	@Autowired private CartItemRepository cartItemRepository;
	@Autowired private PurchaseRespository purchaseRespository;
	@Autowired private CartItemMapper cartItemMapper;
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private ProductRepository productRepository;
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
	public Boolean addproductToCart(Long userId, CartItemRequestDto dto) {
		CartItem item = new CartItem();
		
		UserEntity user = validateAndGetUser(userId);
		
		item.setCuantity(dto.getCuantity());
		item.setProduct(productServiceImpl.validateAndGetProduct(dto.getProductId()));
		item.setUser(user);
		user.getBasket().add(item);
		
		userEntityRepository.save(user);
		
		return true;
	}
	
	@Override
	@Transactional
	public Boolean buyCartItems(Long userId) {
		UserEntity user = validateAndGetUser(userId);

	    int cartPrice = user.getBasket().stream()
	        .mapToInt(item -> {
	            Product product = item.getProduct();
	            if (product == null) {
	                throw new IllegalStateException("Producto no encontrado en el carrito: " + item.getCartId());
	            }
	            return product.getPrice() * item.getCuantity();
	        })
	        .sum();

	    if (user.getPoints() < cartPrice) {
	        throw new NotEnoughPointsException(USER_FAIL_PURCHASE);
	    }

	    for (CartItem item : new ArrayList<>(user.getBasket())) {
	    	Product product = item.getProduct();
	    	//System.out.println("****************************************************: "+ product);
	        Purchase purchase = new Purchase();
	        purchase.setUser(user);
	        System.out.println("****************************************************: "+ purchase.getUser().getUserId());	        
	        purchase.setProduct(product);
	        purchase.setQuantity(item.getCuantity());
	        purchase.setTotalPrice(product.getPrice() * item.getCuantity());
	        purchase.setPurchaseDate(LocalDateTime.now());

	        //newPurchases.add(purchase);
	        product.getBuys().add(purchase);
	        user.getPurchases().add(purchase);
	    }

	    // Descontar puntos y limpiar el carrito
	    user.setPoints(user.getPoints() - cartPrice);
	    user.getBasket().clear();

	    // Persistir cambios
	    //purchaseRespository.saveAll(newPurchases);
	    userEntityRepository.save(user); // Si tienes cascada, esto asegura todo

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
	    CartItem itemToUpdate = user.getBasket().stream()
	            .filter(cartItem -> cartItem.getProduct().getProductId().equals(dto.getProductId()))
	            .findFirst()
	            .orElseThrow(() -> new ResourceNotFoundException(
	                    String.format("Producto con ID %d no encontrado en el carrito del usuario ID %d", dto.getProductId(), userId)));

	    itemToUpdate.setCuantity(dto.getCuantity());
		user.getBasket().stream().map( cartItem -> cartItem.getProduct().getProductId() == dto.getProductId());
		cartItemRepository.save(itemToUpdate);
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
	
    public void buyProduct(Purchase purchase, CartItem item, UserEntity user) {
        purchase.setUser(user);
        purchase.setProduct(item.getProduct());
        purchase.setQuantity(item.getCuantity());
        purchase.setTotalPrice(item.getProduct().getPrice() * item.getCuantity());
        purchase.setPurchaseDate(LocalDateTime.now());
        //purchaseRespository.save(purchase);
	}
}
