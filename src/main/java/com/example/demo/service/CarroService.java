package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CarroDTO;
import com.example.demo.entity.Carro;
import com.example.demo.repository.ICarroRepository;

@Service
public class CarroService {
	
	@Autowired
	ICarroRepository repository;
	
	public Carro salvarCarro(Carro carro) {
		repository.save(carro);
		return carro;
	}
	
	public List<Carro> listarCarros(Long idUsuario){
		return repository.listarCarroPorUsuario(idUsuario);
	}
	
	public Carro listarCarroDoUsuario(Long id,  Long idUsuario){
		return repository.listarCarroDoUsuario(id, idUsuario);
	}
	
	public void removeCarroDoUsuario(Long id,  Long idUsuario) {
		repository.removeCarroDoUsuario(id, idUsuario);
	}
	
	public Carro getById(Long id){
		var carro = repository.findById(id);
		return carro.isPresent() ? carro.get() : null;
	}
	
	public Carro atualizarCarro(Carro carro, CarroDTO dto) {
		if(dto.getYear() != null) {
			carro.setAno(dto.getYear());
		} 
		
		if(dto.getColor() != null) {
			carro.setColor(dto.getColor());
		} 
		
		if(dto.getLicensePlate() != null) {
			carro.setLicensePlate(dto.getLicensePlate());
		} 
		
		if(dto.getModel() != null) {
			carro.setModel(dto.getModel());
		}
		
		return salvarCarro(carro);
	}

}
