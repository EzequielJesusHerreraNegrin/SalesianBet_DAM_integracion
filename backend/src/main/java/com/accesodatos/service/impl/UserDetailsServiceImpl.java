package com.accesodatos.service.impl;

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
import com.accesodatos.dto.auth.AuthRegisterRequestDto;
import com.accesodatos.dto.auth.AuthResponseDto;
import com.accesodatos.entity.Role;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.exception.ResourceNotFoundException;
import com.accesodatos.jwt.JwtTokenProvider;
import com.accesodatos.repository.RoleRepository;
import com.accesodatos.repository.UserEntityRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserEntityRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * The function maps a set of roles to a collection of GrantedAuthority objects
	 * with role names
	 * prefixed with "ROLE_".
	 * 
	 * @param roles The `roles` parameter is a set of `Role` objects. The
	 *              `mapToAuthorities` method takes
	 *              this set of `Role` objects and maps them to a collection of
	 *              `GrantedAuthority` objects. Each `Role`
	 *              object is transformed into a `SimpleGrantedAuthority` object
	 *              with the authority
	 * @return A collection of GrantedAuthority objects is being returned. Each
	 *         GrantedAuthority object is
	 *         created by mapping a Role object to a SimpleGrantedAuthority object
	 *         with the role name prefixed
	 *         with "ROLE_".
	 */
	public Collection<GrantedAuthority> mapToAuthorities(Set<Role> roles) {
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName())))
				.collect(Collectors.toList());
	}

	/**
	 * The function loads user details by username from a repository and maps user
	 * roles to authorities.
	 * 
	 * @param username The `username` parameter in the `loadUserByUsername` method
	 *                 represents the email
	 *                 address of the user whose details are being loaded. It is
	 *                 used to retrieve the user entity from the
	 *                 database based on the provided email address.
	 * @return The method is returning a UserDetails object, which is created using
	 *         the UserEntity
	 *         retrieved from the userRepository. The UserDetails object contains
	 *         the user's email, password, and
	 *         authorities mapped from the user's roles.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(username)
				.orElseThrow(() -> new ResourceNotFoundException("User: " + username + " not found"));
		return new User(
				userEntity.getEmail(),
				userEntity.getPassword(),
				mapToAuthorities(userEntity.getRoles()));
	}

	/**
	 * The function `authenticate` verifies the provided username and password
	 * against the stored user
	 * details and returns an authentication token if successful.
	 * 
	 * @param username The `username` parameter in the `authenticate` method is
	 *                 typically the username
	 *                 provided by the user trying to log in. It is used to retrieve
	 *                 the user details for authentication.
	 * @param password The `password` parameter in the `authenticate` method is the
	 *                 password entered by
	 *                 the user trying to authenticate. It is compared with the
	 *                 password stored in the `UserDetails`
	 *                 object retrieved for the given `username`. If the passwords
	 *                 match, the method creates and returns
	 *                 an `Authentication` object for the
	 * @return The method `authenticate` is returning an `Authentication` object,
	 *         specifically a
	 *         `UsernamePasswordAuthenticationToken` object with the provided
	 *         username, password, and authorities
	 *         obtained from the user details.
	 */
	private Authentication autenticate(String username, String password) {
		UserDetails userDetails = this.loadUserByUsername(username);

		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid username or password");
		}
		return new UsernamePasswordAuthenticationToken(username,
				userDetails.getPassword(),
				userDetails.getAuthorities());
	}

	/**
	 * The `login` function takes in a login request, authenticates the user,
	 * generates a JWT access
	 * token, and returns it in an `AuthResponseDto`.
	 * 
	 * @param authLoginRequestDto The `authLoginRequestDto` parameter is an object
	 *                            of type
	 *                            `AuthLoginRequestDto` which contains the user's
	 *                            email and password information needed for
	 *                            authentication during the login process.
	 * @return An `AuthResponseDto` object is being returned, which contains the
	 *         access token generated by
	 *         the `jwtTokenProvider` after authenticating the user with the
	 *         provided email and password.
	 */
	public AuthResponseDto login(AuthLoginRequestDto authLoginRequestDto) {
		Authentication authentication = this.autenticate(authLoginRequestDto.getEmail(),
				authLoginRequestDto.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String accsessToken = jwtTokenProvider.generateToken(authentication);

		return new AuthResponseDto(accsessToken);
	}

	/**
	 * The function registers a new user by creating a UserEntity object with the
	 * provided information and
	 * saving it to the repository.
	 * 
	 * @param dto The `dto` parameter in the `register` method is an
	 *            `AuthRegisterRequestDto` object that
	 *            contains the user's registration information such as username,
	 *            email, password, DNI (identification
	 *            number), and country.
	 * @return The `register` method returns a `Boolean` value, which is `true`.
	 */
	public Boolean register(AuthRegisterRequestDto dto) {
		UserEntity newUser = new UserEntity();

		newUser.setUserName(dto.getUserName());
		newUser.setEmail(dto.getEmail());
		newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
		newUser.setDni(dto.getDni());
		newUser.setCountry(dto.getCountry());
		newUser.getRoles().add(roleRepository.findByRoleName("USER").orElseThrow(
				() -> new ResourceNotFoundException("Role not found.")));
		newUser.setPoints(1000);

		userRepository.save(newUser);
		return true;
	}

}