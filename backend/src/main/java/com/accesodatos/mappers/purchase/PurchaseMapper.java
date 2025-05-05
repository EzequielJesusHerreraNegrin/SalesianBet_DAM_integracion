
package com.accesodatos.mappers.purchase;

import org.mapstruct.Mapper;

import com.accesodatos.dto.purchase.PurchaseResponseDto;
import com.accesodatos.entity.Purchase;

@Mapper(componentModel = "spring")
public interface PurchaseMapper {

	PurchaseResponseDto toPurchaseResponseDto(Purchase purchase);
}