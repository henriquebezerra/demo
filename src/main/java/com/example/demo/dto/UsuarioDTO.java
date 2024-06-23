package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.entity.Usuario;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UsuarioDTO {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private LocalDate birthday;
	private String login;
	private String password;
	private String phone;
	private LocalDate createdAt;
	private List<CarroDTO> cars;
	
	
	public Usuario fromDto() {
		var usuario = new Usuario();
		usuario.setId(id);
		usuario.setFirstName(firstName);
		usuario.setLastName(lastName);
		usuario.setEmail(email);
		usuario.setBirthday(birthday);
		usuario.setLogin(login);
		usuario.setPassword(password);
		usuario.setPhone(phone);
		if(cars != null && !cars.isEmpty()) {
			var carros = cars.stream().map(CarroDTO::fromDto).collect(Collectors.toList()) ;
			usuario.setCars(carros);
		}
		
		return usuario;
	}
	
	public static UsuarioDTO fromEntity(Usuario usuario, boolean comCarros) {
		var dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setBirthday(usuario.getBirthday());
		dto.setEmail(usuario.getEmail());
		dto.setLogin(usuario.getLogin());
		dto.setFirstName(usuario.getFirstName());
		dto.setLastName(usuario.getLastName());
		dto.setPhone(usuario.getPhone());
		dto.setPassword(usuario.getPassword());
		dto.setCreatedAt(usuario.getCreatedAt());
		if(comCarros && usuario.getCars() != null && !usuario.getCars().isEmpty()){
			var cars = usuario.getCars().stream().map(c -> CarroDTO.fromEntity(c, false)).collect(Collectors.toList());
			dto.setCars(cars);
		}
		return dto;
	}
	
	
}
