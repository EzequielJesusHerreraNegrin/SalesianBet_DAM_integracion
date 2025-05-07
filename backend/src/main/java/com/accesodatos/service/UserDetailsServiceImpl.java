package com.accesodatos.service;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.accesodatos.dto.auth.AuthLoginRequestDto;
import com.accesodatos.dto.auth.AuthResponseDto;
import com.accesodatos.entity.Role;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.jwt.JwtTokenProvider;
import com.accesodatos.repository.UserEntityRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserEntityRepository userRepository;

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Collection<GrantedAuthority> mapToAuthorities(Set<Role> roles) {
		return roles.stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName())))
					.collect(Collectors.toList());
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username)
											  .orElseThrow(() -> 
												  new ResourceNotFoundException("User: " + username + " not found")
											  );
		return new User( 
						 userEntity.getEmail(),
						 userEntity.getPassword(),
						 mapToAuthorities(userEntity.getRoles())
				);
	}
	
	private Authentication autenticate(String username, String password) {
		UserDetails userDetails = this.loadUserByUsername(username);
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(username, 
													   userDetails.getPassword(), 
													   userDetails.getAuthorities()
													   );
	}
	
	public AuthResponseDto login(AuthLoginRequestDto authLoginRequestDto) {
		System.out.println(authLoginRequestDto);
		Authentication authentication = this.autenticate(authLoginRequestDto.getEmail(),
														 authLoginRequestDto.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String accsessToken = jwtTokenProvider.generateToken(authentication);
		
		return new AuthResponseDto(accsessToken);
	}

}