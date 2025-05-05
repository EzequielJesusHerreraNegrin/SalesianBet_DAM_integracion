package com.accesodatos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.entity.CartItem;
import com.accesodatos.entity.Product;
import com.accesodatos.entity.Purchase;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.NotEnoughPointsException;
import com.accesodatos.repository.PurchaseRespository;
import com.accesodatos.repository.UserEntityRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PurchaseServiceImpl implements PurchaseService{
	
	@Autowired private UserEntityRepository userEntityRepository;
	@Autowired private PurchaseRespository purchaseRespository;
	@Autowired private UserEntityServiceImpl userServiceImpl;
	
	private static final String USER_FAIL_PURCHASE = "User purchase fail due to basket total price is higher than the points he owns.";
	
	@Override
	public Boolean buyUserBasketProducts(Long id) {
		    UserEntity user = userServiceImpl.validateAndGetUser(id);
		    log.debug("Starting purchase for user: {}", user.getUserId());
		    int cartPrice = user.getBasket().stream()
		        .mapToInt(item -> {
		            Product product = item.getProduct();
		            return product.getPrice() * item.getCuantity();
		        })
		        .sum();

		    if (user.getPoints() < cartPrice) {
		    	throw new NotEnoughPointsException(USER_FAIL_PURCHASE);
		    }

		    List<Purchase> createdPurchases = new ArrayList<>(); 
		    for (CartItem item : new ArrayList<>(user.getBasket())) {
		    	Product product = item.getProduct();

		        Purchase purchase = new Purchase();
		        purchase.setUser(user);
		        purchase.setProduct(product);
		        purchase.setQuantity(item.getCuantity());
		        purchase.setTotalPrice(product.getPrice() * item.getCuantity());
		        purchase.setPurchaseDate(LocalDateTime.now());

		        purchaseRespository.save(purchase);
		    }

		    user.setPoints(user.getPoints() - cartPrice);

		    user.getBasket().clear();

		    userEntityRepository.save(user);
		    return true;
		}


}
