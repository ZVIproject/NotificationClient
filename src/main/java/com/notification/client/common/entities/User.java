package com.notification.client.common.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	private Integer id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String patronymic;
	private String email;
	
	
	public User() {
		// default constructor
	}
	
	public User(
			Integer id, 
			String username, 
			String password, 
			String firstName, 
			String lastName, 
			String patronymic, 
			String email
		) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.patronymic = patronymic;
		this.email = email;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPatronymic() {
		return patronymic;
	}
	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Filing and return new object of User.
	 * 
	 * @param resultSet
	 * 
	 * @return new User object
	 * 
	 * @throws SQLException
	 */
	public static User getUser(ResultSet resultSet) throws SQLException {
		return new User(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"),
				resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("patronymic"),
				resultSet.getString("email"));
	}
	
}
