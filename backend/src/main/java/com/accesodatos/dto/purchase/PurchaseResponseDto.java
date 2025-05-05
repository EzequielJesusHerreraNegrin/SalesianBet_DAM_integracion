package com.accesodatos.dto.purchase;

import java.time.LocalDateTime;

import com.accesodatos.dto.product.ProductPurchaseResponseDto;
import com.accesodatos.dto.product.ProductResponseDto;
import com.accesodatos.entity.UserEntity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PurchaseResponseDto {

    private Long purchaseId;
    private ProductPurchaseResponseDto product;
    private int quantity;
    private int totalPrice;
    private LocalDateTime purchaseDate;
}
