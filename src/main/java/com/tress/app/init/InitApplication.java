package com.tress.app.init;

import com.tress.app.init.config.CustomUserDetails;
import com.tress.app.init.config.WebMvcConfig;
import com.tress.app.init.entities.role.Role;
import com.tress.app.init.entities.user.User;
import com.tress.app.init.entities.user.UserRepository;
import com.tress.app.init.entities.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableResourceServer
public class InitApplication extends ResourceServerConfigurerAdapter {

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

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/","/home","/register","/login","/posts").permitAll()
				.antMatchers("/userPost").permitAll()
				.antMatchers("/userPost/**").authenticated()
				.antMatchers(HttpMethod.DELETE , "/post/**").hasAuthority("ROLE_ADMIN");
	}
}
