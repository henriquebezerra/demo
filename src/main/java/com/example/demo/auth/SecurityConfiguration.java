package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private FilterToken filter;
	
	
	 protected static final String [] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {
	            "/api/signin",
	            "/api/users",
	            "/api/users/{id}"
    };
	 
	 protected static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
	            "/api/cars",
	            "/api/cars/{id}",
	            "/api/me"
 };
	 
	 @Bean
	 public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		 
		 httpSecurity
		 	.authorizeHttpRequests(authorize -> authorize
			     .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
				 ).csrf(AbstractHttpConfigurer::disable);
		 
		 httpSecurity
		 	.authorizeHttpRequests(authorize -> authorize
			     .requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
				 //.anyRequest().permitAll()
				 ).csrf(AbstractHttpConfigurer::disable)
		 	.addFilterBefore(filter,UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	 }
	 
	 
	 @Bean
	 public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
		 return authConfiguration.getAuthenticationManager();
	 }
	 
	 @Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }

}
