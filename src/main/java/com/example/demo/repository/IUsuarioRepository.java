package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long>, IUsuarioCustom{
	
	@Query("SELECT u FROM Usuario u WHERE u.email = :email")
	public Usuario getByEmail(String email);
	
	@Query("SELECT u FROM Usuario u WHERE u.login = :login AND u.password =:senha")
	public Usuario getLoginSenha(String login, String senha);
	
	@Query("SELECT u FROM Usuario u WHERE u.login = :login")
	public Usuario getByLogin(String login);

}
