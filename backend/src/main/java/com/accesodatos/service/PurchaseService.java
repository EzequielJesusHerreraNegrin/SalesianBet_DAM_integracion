package com.accesodatos.service;

import java.util.List;

import com.accesodatos.dto.purchase.PurchaseResponseDto;

public interface PurchaseService {

	Boolean buyUserBasketProducts(Long id);
	//List<PurchaseResponseDto> getUserPurchases(Long id);
}
