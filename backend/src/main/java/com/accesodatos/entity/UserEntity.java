package com.accesodatos.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = { "purchases", "roles", "bets", "basket" })
@EqualsAndHashCode(exclude = { "purchases", "roles", "bets", "basket" })
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;

	@Column(length = 30, nullable = false)
	private String userName;

	@Column(nullable = false)
	private String password;

	@Column(length = 30, unique = true, nullable = false)
	@Email
	private String email;

	@Column(length = 30, unique = true, nullable = false)
	private String dni;

	private int points;

	private String country;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	private List<CartItem> basket = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "fk_user_id"), inverseJoinColumns = @JoinColumn(name = "fk_role_id"))
	@Builder.Default
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	@Builder.Default
	private List<Bet> bets = new ArrayList<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("user-purchases")
	@Builder.Default
	private List<Purchase> purchases = new ArrayList<>();

	public void addBet(Bet bet) {
		this.bets.add(bet);
		this.setPoints(this.points - bet.getPoints());
	}

	public void removeBet(Bet bet) {
		this.bets.remove(bet);
		this.setPoints(this.points + bet.getPoints());
	}

}
