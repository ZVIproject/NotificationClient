package com.notification.client.interfaces;

import java.util.List;

import com.notification.client.components.entities.User;

public interface IUserDAOService {

	/**
	 * Select all users from DB.
	 * 
	 * @return list of users
	 */
	List<User> getUsers();
	
	/**
	 * Select user by ID.
	 * 
	 * @param id - the ID of the particular user
	 * 
	 * @return user
	 */
	User getUser(Integer id);
	
	/**
	 * Select user by login and password.
	 * 
	 * @param login - the login of the user
	 * @param password - the password of the user
	 * 
	 * @return user
	 */
	User getUser(String login, String password);
	
	/**
	 * Insert new user into DB.
	 * 
	 * @param user - the object of User type
	 * 
	 * @param ID of new user
	 */
	Integer create(User user);
	
	/**
	 * Delete user from DB.
	 * 
	 * @param id - the ID of the particular user
	 * 
	 * @return 'true' if operation was succeed, 'false' otherwise
	 */
	boolean delete(Integer id);
}
