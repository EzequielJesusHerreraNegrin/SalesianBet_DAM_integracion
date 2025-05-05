package com.accesodatos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.accesodatos.entity.Product;
import com.accesodatos.entity.enums.ProductState;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByState(ProductState state);
}
