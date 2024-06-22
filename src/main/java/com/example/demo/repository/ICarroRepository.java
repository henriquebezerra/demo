package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Carro;

@Repository
public interface ICarroRepository extends JpaRepository<Carro, Long>, ICarroCustom{
	
	@Query("SELECT c FROM Carro c WHERE c.usuario.id = :idUsuario")
	public List<Carro> listarCarroPorUsuario(Long idUsuario);
	
	@Query("SELECT c FROM Carro c WHERE c.id = :id AND c.usuario.id = :idUsuario")
	public Carro listarCarroDoUsuario(Long id, Long idUsuario);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Carro c WHERE c.id = :id AND c.usuario.id = :idUsuario")
	public void removeCarroDoUsuario(Long id, Long idUsuario);

}
