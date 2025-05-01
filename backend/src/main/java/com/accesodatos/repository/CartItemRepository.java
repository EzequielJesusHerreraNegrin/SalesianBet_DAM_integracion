package com.accesodatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesodatos.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

}
