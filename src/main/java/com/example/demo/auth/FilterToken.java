package com.example.demo.auth;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.entity.Usuario;
import com.example.demo.service.UsuarioService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter {
	
	@Autowired
	JwtTokenAutenticate tokenAutenticate;
	
	@Autowired
	UsuarioService usuarioService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token;
		var authorizationHeader = request.getHeader("Authorization");
		if(authorizationHeader != null) {
			token = authorizationHeader.replace("Bearer ","");
			var subject = tokenAutenticate.getSubject(token);
			var usuario =  usuarioService.getByEmail(subject);
			var credentials = new Credentials(usuario.getLogin(), usuario.getPassword(), token);
			
			var authentication = new UsernamePasswordAuthenticationToken(usuario, credentials, null);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}
		
		filterChain.doFilter(request, response);
	}
	
	
	public Usuario getUsuarioLogado() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public LocalDateTime getDataLogin() {
		var credentials = (Credentials) SecurityContextHolder.getContext().getAuthentication().getCredentials();
		return tokenAutenticate.getUltimoLogin(credentials.getToken());
	}
	
	

}
