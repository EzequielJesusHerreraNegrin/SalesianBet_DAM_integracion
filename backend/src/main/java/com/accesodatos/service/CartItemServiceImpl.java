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
	    log.debug("Starting purchase for user: {}", user.getUserId()); // Use logger

	    // Calculate total price safely
	    int cartPrice = user.getBasket().stream()
	        .mapToInt(item -> {
	            Product product = item.getProduct();
	            if (product == null) {
	                // This shouldn't happen if data integrity is maintained, but good to check
	                log.error("Null product found in CartItem ID: {} for User ID: {}", item.getCartId(), userId);
	                throw new IllegalStateException("Producto nulo encontrado en el carrito para el ítem ID: " + item.getCartId());
	            }
	            log.debug("Calculating price for product ID: {}, price: {}, quantity: {}", product.getProductId(), product.getPrice(), item.getCuantity());
	            return product.getPrice() * item.getCuantity();
	        })
	        .sum();

	    log.debug("User ID: {} - Current Points: {}, Cart Total Price: {}", userId, user.getPoints(), cartPrice);

	    if (user.getPoints() < cartPrice) {
	        log.warn("User ID: {} purchase failed. Points: {}, Required: {}", userId, user.getPoints(), cartPrice);
	        throw new NotEnoughPointsException(USER_FAIL_PURCHASE);
	    }

	    // Create Purchase objects but don't save them individually
	    // Use a temporary list if needed, though adding directly to user.getPurchases() is fine
	    List<Purchase> createdPurchases = new ArrayList<>(); 
	    for (CartItem item : new ArrayList<>(user.getBasket())) { // Iterate copy
	        Product product = item.getProduct(); // Already fetched with user/basket typically
	        if (product == null) { // Defensive check
	             log.error("Null product encountered again for CartItem ID: {} during Purchase creation", item.getCartId());
	             throw new IllegalStateException("Producto nulo encontrado al crear la compra para el ítem ID: " + item.getCartId());
	        }

	        Purchase purchase = new Purchase();
	        purchase.setUser(user);
	        purchase.setProduct(product);
	        purchase.setQuantity(item.getCuantity());
	        purchase.setTotalPrice(product.getPrice() * item.getCuantity());
	        purchase.setPurchaseDate(LocalDateTime.now());

	        // Add to collections to maintain bidirectional consistency IN MEMORY
	        user.getPurchases().add(purchase);
	        product.getBuys().add(purchase);
	        // createdPurchases.add(purchase); // Optional: if you need the list later

	        // --- REMOVED ---
	        // purchaseRespository.save(purchase); 
	        // --- REMOVED ---
	        log.debug("Prepared Purchase for Product ID: {} by User ID: {}", product.getProductId(), userId);
	    }

	    // Update user state
	    log.debug("Updating User ID: {} points. Old: {}, New: {}", userId, user.getPoints(), user.getPoints() - cartPrice);
	    user.setPoints(user.getPoints() - cartPrice);

	    log.debug("Clearing basket for User ID: {}", userId);
	    user.getBasket().clear(); // Triggers CartItem removal due to orphanRemoval

	    // Save the user. CascadeType.ALL on 'purchases' will save the new Purchase entities.
	    // CascadeType.ALL + orphanRemoval=true on 'basket' will delete the CartItems.
	    log.info("Saving UserEntity (ID: {}) which will cascade saves to Purchases and deletions of CartItems", userId);
	    userEntityRepository.save(user);
	    log.info("UserEntity (ID: {}) saved successfully.", userId);

	    // System.out.println(" HOASDASLDHASLKJDHALSKJD"); // Use logger instead
	    log.debug("Purchase process completed successfully for User ID: {}", userId);

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
