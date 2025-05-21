package com.accesodatos.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.exception.TokenExpiredException;
import com.accesodatos.repository.UserEntityRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

	@Value("${security.jwt.key.private}")
	private String privateKey;
	
	@Autowired UserEntityRepository entityRepository;
	
	private static final long JWT_EXPIRATION_DATE = 3600000;
	
	
	public String generateToken(Authentication authentication) {
		UserEntity user = entityRepository.findByEmail(authentication.getName())
			    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
		String username = authentication.getName();
		List<String> role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
		Date currentDate = new Date();
		Date expirate = new Date(currentDate.getTime() + JWT_EXPIRATION_DATE);
		Map<String, Object> claims = new HashMap<>();
		claims.put("roles", role);
		claims.put("userId", user.getUserId());
		
		return Jwts.builder()
				.subject(username)
				.issuedAt(currentDate)
				.expiration(expirate)
				.claims(claims)
				.signWith(getSignInKey(), Jwts.SIG.HS256)
				.compact();
	}
	
	private SecretKey getSignInKey() {
		byte[] keyBites = Decoders.BASE64.decode(privateKey);		
		return Keys.hmacShaKeyFor(keyBites);
	}
	
	public String getSubjectFromToken(String token) {
		return extraClaim(token, Claims::getSubject);
	}
	
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	private Date extractExpiration(String token) {
		return extraClaim(token, Claims::getExpiration);
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser()
			.verifyWith(getSignInKey())
			.build()
			.parseSignedClaims(token);
			
			if (isTokenExpired(token)) {
				throw new TokenExpiredException("The life time of the token has ENDED, Login to get a new one.");
			}
			
			return true;			
		} catch (Error e) {
			throw new RuntimeErrorException(e, "Token fail validation filter.");
		}
	}
	
	private <T> T extraClaim(String token, Function<Claims, T> claimResolver) { 
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				   .verifyWith(getSignInKey())
				   .build()
				   .parseSignedClaims(token)
				   .getPayload();
	}
}
