package com.example.demo.dto;

import com.example.demo.entity.Carro;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CarroDTO {
	
	private Long id;
	private Integer year;
	private String licensePlate;
	private String model;
	private String color;
	private UsuarioDTO usuario;
	
	public Carro fromDto() {
		var carro = new Carro();
		carro.setId(id);
		carro.setAno(year);
		carro.setLicensePlate(licensePlate);
		carro.setModel(model);
		carro.setColor(color);
		carro.setUsuario(usuario != null ? usuario.fromDto() : null);
		return carro;
	}
	
	public static CarroDTO fromEntity(Carro carro, boolean comUsuario) {
		var dto = new CarroDTO();
		dto.setId(carro.getId());
		dto.setYear(carro.getAno());
		dto.setColor(carro.getColor());
		dto.setModel(carro.getModel());
		dto.setLicensePlate(carro.getLicensePlate());
		if(comUsuario) {
			dto.setUsuario(UsuarioDTO.fromEntity(carro.getUsuario(), false));
		}
		
		return dto;
	}

}
