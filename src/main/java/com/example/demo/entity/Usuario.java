package com.example.demo.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String firstName;
	private String lastName;
	
	@Email
	private String email;
	
	private LocalDate birthday;
	
	private String login;
	private String password;
	private String phone;
	
	private LocalDate createdAt;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.PERSIST)
	private List<Carro> cars;
	
	
	
	

}
