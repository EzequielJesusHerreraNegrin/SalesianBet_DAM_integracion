package com.accesodatos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.accesodatos.jwt.JwtAuthenticationFilter;
import com.accesodatos.service.impl.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private static final String[] GENERAL_PATHS = {
			"/doc/swagger-ui/**",
			"/doc/swagger-ui.html",
			"/v3/api-docs/**",
			"/api/v1/auth/**",
			"/api/v1/products",
			"/api/v1/matches",
			"/api/v1/matches/{matchesId}",
			"/api/v1/matches/by-date",
			"/api/v1/competitions",
			"/api/v1/competitions/{competitionId}",
			"/api/v1/teams",
			"/api/v1/teams/{teamId}",

	};

	private static final String[] USER_PATHS = {
			"/api/v1/users/email",
			"/api/v1/users/purchase/{userId}",
			"/api/v1/bets/{betId}",
			"/api/v1/bets/{email}",
			"/api/v1/bets/email",
			"/api/v1/cartItems/product/{userId}",
			"/api/v1/cartItems/{userId}/product/{productId}",

	};

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(csrf -> csrf.disable())
				.cors(Customizer.withDefaults())
				.authorizeHttpRequests(auth -> auth.requestMatchers(HttpMethod.GET,
						GENERAL_PATHS).permitAll()
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()
						.requestMatchers(USER_PATHS).hasRole("USER")
						.requestMatchers(HttpMethod.POST, "/api/v1/bets").hasRole("USER")
						.requestMatchers("/**").hasRole("ADMIN")
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.httpBasic(Customizer.withDefaults())
				.build();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {

		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);

		return provider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
}
