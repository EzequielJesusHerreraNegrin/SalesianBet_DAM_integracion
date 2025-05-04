package com.accesodatos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.accesodatos.entity.Purchase;

public interface PurchaseRespository extends JpaRepository<Purchase, Long>{

}
