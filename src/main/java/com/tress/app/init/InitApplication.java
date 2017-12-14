package com.tress.app.init;

import com.tress.app.init.config.CustomUserDetails;
import com.tress.app.init.config.WebMvcConfig;
import com.tress.app.init.role.Role;
import com.tress.app.init.user.User;
import com.tress.app.init.user.UserRepository;
import com.tress.app.init.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Arrays;

@SpringBootApplication
public class InitApplication {

	@Autowired
	private WebMvcConfig webMvcConfig;

	public static void main(String[] args) {
		SpringApplication.run(InitApplication.class, args);
	}

	/**
	 * Password grants are switched on by injecting an AuthenticationManager.
	 * Here, we setup the builder so that the userDetailsService is the one we coded.
	 * @param builder
	 * @param userRepository
	 * @throws Exception
	 */
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository userRepository, UserService userService) throws Exception {
		if (userRepository.count()==0)
			userService.saveUser(new User("admin", "adminPassword", Arrays.asList(new Role("USER"), new Role("ACTUATOR") , new Role("ADMIN")), "sebastian@s.com", "salinas"));
			builder.userDetailsService(userDetailsService(userRepository)).passwordEncoder(webMvcConfig.passwordEncoder());
	}

	/**
	 * We return an istance of our CustomUserDetails.
	 * @param userRepository
	 * @return
	 */
	private UserDetailsService userDetailsService(final UserRepository userRepository) {
		return username -> new CustomUserDetails(userRepository.findByUsername(username));

		//oauth/token?grant_type=password&username=admin&password=adminPassword

	}
}
