package com.notification.client.components.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;

public class User {

	private Integer id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String position;
	private Date created;
	private Date modified;
	
	public User() {
		// default constructor
	}
	
	public User(
			Integer id, 
			String username, 
			String password, 
			String firstName, 
			String lastName,
			String position,
			Date created,
			Date modified
	) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.position = position;
		this.created = created;
		this.modified = modified;
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
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * Filing and return new object of User. 
	 * @param resultSet 
	 * @return new User object
	 * @throws SQLException
	 */
	public static User getUser(ResultSet resultSet) throws SQLException {
		User user = new User();
		user.setId(resultSet.getInt("id"));
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setFirstName(resultSet.getString("first_name"));
		user.setLastName(resultSet.getString("last_name"));
		user.setPosition(resultSet.getString("position"));
		user.setCreated(resultSet.getDate("created"));
		user.setModified(resultSet.getDate("modified"));
		return user;
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
		user.setPosition(cells.get(4).getStringCellValue());
		
		return user;
	}
	
}
