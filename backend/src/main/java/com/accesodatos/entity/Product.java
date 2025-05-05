package com.accesodatos.entity;

import java.util.ArrayList;
import java.util.List;

import com.accesodatos.entity.enums.ProductState;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "buys")
@ToString(exclude = "buys")
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;
	
	private String productName;
	
	private String productImage;
	
	private int price;
	
	@Enumerated(EnumType.STRING)
	private ProductState state;
	
	@OneToMany(
			mappedBy = "product",
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	@JsonManagedReference("product-purchases")
	private List<Purchase> buys = new ArrayList<>();	
}
