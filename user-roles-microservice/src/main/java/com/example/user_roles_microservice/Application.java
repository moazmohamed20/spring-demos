package com.example.user_roles_microservice;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.user_roles_microservice.entity.Role;
import com.example.user_roles_microservice.entity.User;
import com.example.user_roles_microservice.repository.RolesRepository;
import com.example.user_roles_microservice.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	private final UsersRepository usersRepository;
	private final RolesRepository rolesRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		Role agentRole = rolesRepository.findByName("AGENT").orElse(Role.builder().name("AGENT").build());
		Role adminRole = rolesRepository.findByName("ADMIN").orElse(Role.builder().name("ADMIN").build());
		adminRole = rolesRepository.save(adminRole);

		User u1 = User.builder()
				.username("user1")
				.password("password1")
				.email("user1@email.com")
				.build()
				.addRole(adminRole);
		usersRepository.save(u1);

		User u2 = User.builder()
				.username("user2")
				.password("password2")
				.build()
				.addRole(adminRole)
				.addRole(agentRole);
		usersRepository.save(u2);

		u1.addRole(agentRole);
		usersRepository.save(u1);
	}

}
