package com.accesodatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesodatos.entity.CartItem;
import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Long>{

	List<CartItem> findByUserUserId(Long userId);
}
