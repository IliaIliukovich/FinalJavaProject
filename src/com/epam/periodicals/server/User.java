package com.epam.periodicals.server;

public class User {

	private Long id;
	private String login;
	private String password;
	
	public User(Long id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
