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
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;
import java.util.Arrays;

@SpringBootApplication
@EnableOAuth2Client
public class InitApplication extends WebSecurityConfigurerAdapter {

	@Autowired
	private WebMvcConfig webMvcConfig;

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

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
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll().anyRequest()
				.authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and().logout()
				.logoutSuccessUrl("/").permitAll().and().csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
		// @formatter:on
	}

	private Filter ssoFilter() {
		OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login/facebook");
		OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
		facebookFilter.setRestTemplate(facebookTemplate);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(facebookResource().getUserInfoUri(),
				facebook().getClientId());
		tokenServices.setRestTemplate(facebookTemplate);
		facebookFilter.setTokenServices(
				new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
		return facebookFilter;
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}

	@Bean
	@ConfigurationProperties("facebook.client")
	public AuthorizationCodeResourceDetails facebook() {
		return new AuthorizationCodeResourceDetails();
	}

	@Bean
	@ConfigurationProperties("facebook.resource")
	public ResourceServerProperties facebookResource() {
		return new ResourceServerProperties();
	}
}
