package com.accesodatos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.accesodatos.jwt.JwtAuthenticationFilter;

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
			 "/api/v1/competitions",			 
	};

	private static final String[] USER_PATHS = {
			"/api/v1/users/{email}",
			"/api/v1/users/purchase/{userId}",
			"/api/v1/users/bets",
			"/api/v1/users/bets/{betId}",
			"/api/v1/users/bets/{email}",
			"/api/v1/cartItems/product/{id}",
			"/api/v1/cartItems/{userId}/product/{productId}",
			"/api/v1/bets",
			"/api/v1/bets/{betId}",
			"/api/v1/bets/{email}",
			"/api/v1/matches/by-date",			
	};
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				// desactiva la protección de inicio de sesión de coockies de sesión, porque usaremos JWT
				.csrf(csrf -> csrf.disable())
				// habilita la configuración de CORS, mecanismo de solicitud de recusos de las páginas web modernas
				.cors(Customizer.withDefaults())
				// Método para definir las reglas de autorización
				.authorizeHttpRequests(auth -> 
					// exime del acceso autorizados a los endpoints señalados si termina en "permit.all"
					auth.requestMatchers(HttpMethod.GET, 
													GENERAL_PATHS).permitAll()
						.requestMatchers(HttpMethod.POST,"/api/v1/auth/**").permitAll()
						// requiere que el user tenga rol ADMIN para acceder	
						.requestMatchers(HttpMethod.GET, "/api/v1/products").hasRole("USER")
						.requestMatchers("/**").hasRole("ADMIN")
						.anyRequest().authenticated()
				)
				// Para que el servidor del navegador no guarde la sesión del cliente, solo el propio equipo del cliente guarda la información del token.
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// tipo de filtro de seguridad por petición, usuario y contraseña
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				// 
				.httpBasic(Customizer.withDefaults())
				.build();
	}
	
	// Función que valida si el usuario está autenticado.
//	@Bean
//	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//		return authenticationConfiguration.getAuthenticationManager();
//	}
	
	// Función específica para autenticar usuarios 
//	@Bean
//	AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
//		
//		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//		
//		provider.setPasswordEncoder(passwordEncoder());
//		provider.setUserDetailsService(userDetailsService);
//		
//		return provider;
//	}
	
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}
}
