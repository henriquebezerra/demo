package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInfoDTO {
	
	private UsuarioDTO usuario;
	private LocalDateTime lastLogin;

}
