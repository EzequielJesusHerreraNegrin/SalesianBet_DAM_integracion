package com.accesodatos.configuration;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.accesodatos.entity.Role;
import com.accesodatos.entity.UserEntity;
import com.accesodatos.repository.UserEntityRepository;

@Configuration
public class LoadDatabase {


	@Bean
	CommandLineRunner initDatabase(UserEntityRepository userRepository) {
		return arg -> {
			
			Role roleAdmin = Role.builder()
								 .roleName("ADMIN")
								 .build();
			
			Role roleUser = Role.builder()
					 			.roleName("USER")
					 			.build();
			
			UserEntity userAdmin = UserEntity.builder()
											.userName("admin")
											.password("$2a$10$xezulZ7payMdpS0v.t/1OeRKZr95hYakhLkpmJI2H8JsaMGQ3Adi2")
											.roles(Set.of(roleAdmin))
											.build();
			
			
			//userRepository.saveAll(List.of(userAlonso, userJose, userDaniel, userAndres));
			
		};
	}
}
