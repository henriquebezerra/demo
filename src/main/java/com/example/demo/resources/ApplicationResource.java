package com.example.demo.resources;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.auth.Credentials;
import com.example.demo.auth.FilterToken;
import com.example.demo.dto.CarroDTO;
import com.example.demo.dto.UserInfoDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.entity.Usuario;
import com.example.demo.service.CarroService;
import com.example.demo.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class ApplicationResource {
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	UsuarioService usuarioService;
			
	@Autowired
	FilterToken filter;
	

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
	  return String.format("Hello %s!", name);
	}
	
	
	@PostMapping("/signin")
	public ResponseEntity<Credentials> signin(@RequestBody Credentials credentials){
		return ResponseEntity.ok(usuarioService.autenticar(credentials));
	}
	
	@PostMapping("/users")
	public ResponseEntity<UsuarioDTO> cadastrarUsuario(@Valid @RequestBody UsuarioDTO dto){
		var usuario = usuarioService.salvarNovoUsuario(dto);
		if(usuario.getCars() != null && !usuario.getCars().isEmpty()) {
			usuario.getCars().forEach(c -> {
				c.setUsuario(new Usuario());
				c.getUsuario().setId(usuario.getId());
				carroService.salvarCarro(c);
			});
			
		}
		return ResponseEntity.ok(UsuarioDTO.fromEntity(usuario, true));
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<UsuarioDTO>> getUsuarios(){
		var listDto = usuarioService.getAllUsers().stream()
				.map(user -> UsuarioDTO.fromEntity(user, false))
					.collect(Collectors.toList());
		return ResponseEntity.ok(listDto);
	}
	
	@GetMapping("/users/{id}")
	public ResponseEntity<Usuario> getUsuarios(@PathVariable Long id){
		return ResponseEntity.ok(usuarioService.getUsuario(id));
	}
	
	
	@DeleteMapping("/users/{id}")
	public void removeUsuario(@PathVariable Long id){
		usuarioService.remove(id);
	}
	
	@PutMapping("/users/{id}")
	public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestBody UsuarioDTO dto, @PathVariable Long id){
		dto.setId(id);
		var usuario = usuarioService.salvarNovoUsuario(dto);
		return ResponseEntity.ok(UsuarioDTO.fromEntity(usuario, false));
	}	
	
	/*ENDPOINT PARA CARROS */
	
	@GetMapping("/cars")
	public ResponseEntity<List<CarroDTO>> getCarros(){
		var logado = filter.getUsuarioLogado();
		var listCars = carroService.listarCarros(logado.getId()).stream().map(c -> CarroDTO.fromEntity(c, true)).collect(Collectors.toList());
		return ResponseEntity.ok(listCars);
	}
	
	
	@PostMapping("/cars")
	public ResponseEntity<CarroDTO> cadastrarCarro(@RequestBody CarroDTO dto){
		var carro = dto.fromDto();
		var logado = filter.getUsuarioLogado();
		carro.setUsuario(logado);
		return ResponseEntity.ok(CarroDTO.fromEntity(carroService.salvarCarro(carro), true));
	}
	
	@GetMapping("/cars/{id}")
	public ResponseEntity<CarroDTO> getCarroId(@PathVariable Long id){
		var logado = filter.getUsuarioLogado();
		return ResponseEntity.ok(CarroDTO.fromEntity(carroService.listarCarroDoUsuario(id, logado.getId()), true));
	}
	
	@DeleteMapping("/cars/{id}")
	public void removeCarroId(@PathVariable Long id){
		var logado = filter.getUsuarioLogado();
		carroService.removeCarroDoUsuario(id, logado.getId());
		
	}
	
	@PutMapping("/cars/{id}")
	public ResponseEntity<CarroDTO> atualizarCarro(@PathVariable Long id, @RequestBody CarroDTO dto){
		var logado = filter.getUsuarioLogado();
		var carro = carroService.listarCarroDoUsuario(id, logado.getId());
		carro = carroService.atualizarCarro(carro, dto);
		return ResponseEntity.ok(CarroDTO.fromEntity(carro, false));
	}
	
	
	@GetMapping("/me")
	public ResponseEntity<UserInfoDTO> getMe(){
		var logado = filter.getUsuarioLogado();
		var userDto = UsuarioDTO.fromEntity(logado, false);
		var userInfo = new UserInfoDTO();
		userInfo.setUsuario(userDto);
		userInfo.setLastLogin(filter.getDataLogin());
		return ResponseEntity.ok(userInfo);
	}
  
}
