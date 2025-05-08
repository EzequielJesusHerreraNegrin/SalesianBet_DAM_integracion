package com.accesodatos.entity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
import jakarta.persistence.ManyToOne;
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
@Table(name = "matches")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "match_id")
	private Long matchId;
	
	private LocalDateTime date;
	
	private Boolean is_playing;
	
	@Column(length = 40)
	private String result;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "home_team_id", nullable = false)
	@JsonBackReference
	private Team homeTeam;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "away_team_id", nullable = false)
	@JsonBackReference
	private Team awayTeam;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "competition_id", nullable = false)
	@JsonBackReference
	private Competition competition;
	
	@OneToMany(
			mappedBy = "match",
			cascade = CascadeType.ALL,
			orphanRemoval = true
			)
	@JsonManagedReference
	private List<Bet> bets = new ArrayList<>();
}
