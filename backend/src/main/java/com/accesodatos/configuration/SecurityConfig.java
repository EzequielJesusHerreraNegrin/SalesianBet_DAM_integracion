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

@Configuration
// @EnableWebSecurity(debug = true)
@EnableMethodSecurity
public class SecurityConfig {
	
	
	/*
	 * public static void main(String[] args) { System.out.println(new
	 * BCryptPasswordEncoder().encode("1234")); }
	 */
	// $2a$10$WuJ7nzkzSHzZJZhNwOL2/OSYrX5PJStblUrnWl2QrKI2LyV6L1i/S

	
	
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
					auth.requestMatchers(HttpMethod.GET, //"/api/writers/ping",
														 "/doc/swagger-ui/**",
														 "/doc/swagger-ui.html",
														 "/v3/api-docs/**"
														 ).permitAll()
						.requestMatchers(HttpMethod.POST,"/api/auth/**").permitAll()
						// requiere que el user tenga rol ADMIN para acceder	
						.requestMatchers(HttpMethod.GET, "/api/writers/onlycreate").hasRole("ADMIN")
						//.requestMatchers(HttpMethod.GET, "/api/writers/onlycreate").hasAuthority("CREATE")
						.anyRequest().authenticated()
				)
				// Para que el servidor del navegador no guarde la sesión del cliente, solo el propio equipo del cliente guarda la información del token.
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// tipo de filtro de seguridad por petición, usuario y contraseña
				//.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
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
	
	
	// Método para encriptar la contraseña
//	@Bean
//	PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
	
//	@Bean
//	JwtAuthenticationFilter jwtAuthenticationFilter() {
//		return new JwtAuthenticationFilter();
//	}
//	
	
	
}
