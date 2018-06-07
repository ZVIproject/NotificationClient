package com.notification.client.services.dao;

import com.notification.client.components.entities.RemoteUser;
import com.notification.client.interfaces.dao.UserDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserDAOServiceImpl implements UserDAOService {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOServiceImpl.class);

    private static final String URL = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    private static final String QUERY_FIND_ALL = "";
    private static final String QUERY_FIND_BY_FIRST_NAME_AND_LAST_NAME = "";


    @Override
    public List<RemoteUser> getAll() {

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_FIND_ALL);
            ResultSet resultSet = statement.executeQuery();

            List<RemoteUser> users = new ArrayList<>();

            while (resultSet.next()) {
                RemoteUser user = new RemoteUser();
                // TODO: fill the fields
                users.add(user);
            }

            return users;

        } catch (SQLException e) {
            logger.error("Exception during users retrieving", e);
            return null;
        }
    }

    @Override
    public RemoteUser getUserByFirstAndLastNames(String firstName, String lastName) {

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BY_FIRST_NAME_AND_LAST_NAME);
            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            RemoteUser user = new RemoteUser();
            // TODO: fill the fields

            return user;

        } catch (SQLException e) {
            logger.error("Exception during user retrieving", e);
            return null;
        }
    }

    /**
     * This method create and return a connection
     * @return Connection
     * @throws SQLException - triggered when application is not able to connect to database
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
