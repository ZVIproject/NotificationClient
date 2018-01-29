package com.notification.client.common.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;

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
	 * @param resultSet 
	 * @return new User object
	 * @throws SQLException
	 */
	public static User getUser(ResultSet resultSet) throws SQLException {
		return new User(
				resultSet.getInt("id"), 
				resultSet.getString("username"), 
				resultSet.getString("password"),
				resultSet.getString("first_name"), 
				resultSet.getString("last_name"), 
				resultSet.getString("patronymic"),
				resultSet.getString("email")
			);
	}
	
	/**
	 * Filling User object using data from list of cells, and return it.
	 * @param cells - list of cells
	 * @return User object
	 */
	public static User getUser(List<Cell> cells) {
		if(cells == null || cells.isEmpty()) {
			return null;
		}
		
		User user = new User();
		user.setUsername(cells.get(0).getStringCellValue());
		user.setPassword(cells.get(1).getStringCellValue());
		user.setFirstName(cells.get(2).getStringCellValue());
		user.setLastName(cells.get(3).getStringCellValue());
		user.setPatronymic(cells.get(4).getStringCellValue());
		user.setEmail(cells.get(5).getStringCellValue());
		
		return user;
	}
	
}
