package com.accesodatos.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accesodatos.entity.CartItem;
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
		int cartPrice = user.getBasket().stream().mapToInt( item -> 
			item.getProduct().getPrice() * item.getCuantity())
			.sum();
		
		if (user.getPoints() >= cartPrice) {
			user.getBasket().stream().forEach( item -> {
				buyProduct(new Purchase(), item);
				System.out.println("COMPRA: ");
			});
		} else {
		    throw new NotEnoughPointsException(USER_FAIL_PURCHASE);
		}
		user.getBasket().clear();
		userEntityRepository.save(user);
		return true;
	}
	
    public void buyProduct(Purchase purchase, CartItem item) {
    	purchase.setUser(item.getUser());
    	purchase.setProduct(item.getProduct());
    	purchase.setQuantity(item.getCuantity());
    	purchase.setTotalPrice(item.getProduct().getPrice() * item.getCuantity());
    	purchase.setPurchaseDate(LocalDateTime.now());
    	item.getUser().setPoints(item.getUser().getPoints() - purchase.getTotalPrice());
	}


}
