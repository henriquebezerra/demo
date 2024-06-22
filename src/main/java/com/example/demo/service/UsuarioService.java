package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.auth.Credentials;
import com.example.demo.auth.JwtTokenAutenticate;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.exception.BusinessException;
import com.example.demo.repository.IUsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	IUsuarioRepository  repository;
	
	@Autowired
	JwtTokenAutenticate tokenAutenticate;
	
	public Credentials autenticar(Credentials credentials) {
		var usuario = repository.getLoginSenha(credentials.getLogin(), credentials.getPassword());
		if(usuario != null) {
			var token = tokenAutenticate.generateToken(credentials.getLogin());
			credentials.setToken(token);
		} else throw new BusinessException("Invalid login or password", HttpStatus.UNAUTHORIZED.value());
		
		return credentials;
		
	}
	
		
	public List<Usuario> getAllUsers(){
		return repository.findAll();
	}
	
	public Usuario getUsuario(Long id) {
		var usuario = repository.findById(id);
		return usuario.isEmpty() ? null : usuario.get();
	}
	
	public Usuario salvar(Usuario user) {
		repository.save(user);
		return user;
	}
	
	public Usuario getByEmail(String email) {
		return repository.getByEmail(email);
	}
	
	public void remove(Long id) {
		repository.deleteById(id);
	}
	
	public Usuario salvarNovoUsuario(UsuarioDTO dto) {
		validarUsuario(dto);
		var usuario = dto.fromDto();
		usuario.setCreatedAt(LocalDate.now());
		return salvar(usuario);
	}
	
	private void validarUsuario(UsuarioDTO dto) {
		
		if((dto.getFirstName() == null || dto.getFirstName().isBlank()) 
				|| (dto.getLastName() == null ||  dto.getLastName().isBlank()) 
				|| (dto.getEmail() == null || dto.getEmail().isBlank()) 
				|| (dto.getLogin() == null || dto.getLogin().isBlank())
				|| (dto.getPassword() == null || dto.getPassword().isBlank())
				|| (dto.getPhone() == null || dto.getPhone().isBlank())){
			throw new BusinessException("Missing fields", HttpStatus.BAD_REQUEST.value());
		}
		
		if(dto.getFirstName().matches(".*\\d.*")		
				|| dto.getLastName().matches(".*\\d.*")) {
			throw new BusinessException("Invalid fields", HttpStatus.BAD_REQUEST.value());
		}
		
		var usuario = repository.getByEmail(dto.getEmail());
		if(usuario != null) {
			throw new BusinessException("Email already exists", HttpStatus.CONFLICT.value());
		}
		
		usuario = repository.getByLogin(dto.getLogin());
		
		if(usuario != null) {
			throw new BusinessException("Login already exists", HttpStatus.CONFLICT.value());
		}
	}

}
