package com.notification.client.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.notification.client.components.entities.User;
import com.notification.client.interfaces.ILoggerService;
import com.notification.client.interfaces.IUserDAOService;
import com.notification.client.services.LoggerService;

public class UserDAOService implements IUserDAOService {

	private static final String URL = "jdbc:mysql://localhost:3306/notification_system";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	
	private static final String QUERY_SELECT_ALL = 
			"SELECT id, username, password, first_name, last_name, patronymic, email FROM users";
	private static final String QUERY_SELECT_BY_ID = 
			"SELECT id, username, password, first_name, last_name, patronymic, email FROM users WHERE id=?";
	private static final String QUERY_SELECT_BY_LOGIN_PASSWORD = 
			"SELECT id, username, password, first_name, last_name, patronymic, email FROM users " + 
			"WHERE username=? AND password=?";
	private static final String QUERY_INSERT = 
			"INSERT INTO notification_system.users(username, password, first_name, last_name, patronymic, email) "
			+ "VALUES (?, ?, ?, ?, ?, ?)";
	private static final String QUERY_DELETE = "DELETE FROM users WHERE id=?";
	
	private ILoggerService loggerService;
	
	
	public UserDAOService() {
		loggerService = new LoggerService();
	}
	
	public Connection getConnection() throws SQLException {
		loggerService.logInfo("Connecting...");
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	/**
	 * @see com.notification.client.common.interfaces.IUserDAOService#getUsers()
	 */
	@Override
	public List<User> getUsers() {
		
		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_ALL);
			ResultSet resultSet = statement.executeQuery();
			
			List<User> usersList = new ArrayList<>();
			
			while(resultSet.next()) {
				usersList.add(User.getUser(resultSet));
			}
			
			return usersList;			
			
		} catch(SQLException e) {
			loggerService.logError(e, "Exception during selection of all users");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see com.notification.client.common.interfaces.IUserDAOService#getUser(Integer)
	 */
	@Override
	public User getUser(Integer id) {
		
		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			
			resultSet.next();
			User user = User.getUser(resultSet);
			
			return user;
			
		} catch(SQLException e) {
			loggerService.logError(e, "Exception during user selection");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see com.notification.client.common.interfaces.IUserDAOService#getUser(String, String)
	 */
	@Override
	public User getUser(String login, String password) {
		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_LOGIN_PASSWORD);
			statement.setString(1, login);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			
			resultSet.next();
			User user = User.getUser(resultSet);
			
			return user;
			
		} catch(SQLException e) {
			loggerService.logError(e, "Exception during user selection");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see com.notification.client.common.interfaces.IUserDAOService#create(User)
	 */
	@Override
	public Integer create(User user) {
		
		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
//			statement.setString(5, user.getPatronymic());
//			statement.setString(6, user.getEmail());
			statement.execute();
			
			ResultSet generatedKeys = statement.getGeneratedKeys();			
			generatedKeys.next();
			
			return generatedKeys.getInt(1);
			
		} catch(SQLException e) {
			loggerService.logError(e, "Exception during inserting");
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see com.notification.client.common.interfaces.IUserDAOService#delete(Integer)
	 */
	@Override
	public boolean delete(Integer id) {
		
		try (Connection connection = getConnection()) {
			PreparedStatement statement = connection.prepareStatement(QUERY_DELETE);
			statement.setInt(1, id);
			statement.execute();
			
			return true;
			
		} catch(SQLException e) {
			loggerService.logError(e, "Exception during deleting");
			throw new RuntimeException(e);
		}
	}	
	
}
