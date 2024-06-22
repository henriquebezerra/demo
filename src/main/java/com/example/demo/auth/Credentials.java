package com.example.demo.auth;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Credentials {
	
	private String login;
	
	private String password;
	
	private String token;
	
	public Credentials() {}
	
	public Credentials (String login, String password, String token) {
		this.login = login;
		this.password = password;
		this.token = token;
	}

}
