package com.accesodatos.configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable() // Desactivar CSRF
            .authorizeRequests()
            .anyRequest().permitAll(); // Permitir acceso a todo sin autenticación
        return http.build();
    }
}
