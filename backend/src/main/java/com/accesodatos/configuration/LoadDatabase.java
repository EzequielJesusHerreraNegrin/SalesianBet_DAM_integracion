package com.accesodatos.configuration;

import java.util.List;
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

			UserEntity userAdmin = UserEntity.builder()
					.userName("admin")
					.password("$2a$10$V.9kO3/QWR/J19M2RB2VFuMtWwzVbkgTsqULZE7N0yaLW3xmdQMPG") // 1234
					.dni("00000000A")
					.email("admin@gmail.com")
					.roles(Set.of(roleAdmin))
					.build();

			// userRepository.saveAll(List.of(userAdmin));

		};
	}
}
