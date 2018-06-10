package com.notification.client.services.dao;

import com.notification.client.components.entities.Employee;
import com.notification.client.configs.DatabaseConfig;
import com.notification.client.interfaces.dao.EmployeeDAOService;
import com.notification.client.services.LoggerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class EmployeeDAOServiceImpl implements EmployeeDAOService {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    private static final String QUERY_FIND_BY_FIRST_NAME_AND_LAST_NAME =
            "SELECT `id`, `first_name`, `middle_name`, `last_name` FROM `employee` WHERE `first_name`=? AND `last_name`=?";

    @Override
    public Employee getUserByFirstAndLastNames(String firstName, String lastName) {

        try (Connection connection = getConnection()) {
            logger.logInfo("Connected");
            PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BY_FIRST_NAME_AND_LAST_NAME);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            logger.logInfo("prepared statement with " + firstName + " " + lastName);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            logger.logInfo("Teacher was found");
            return Employee.getEmployee(resultSet);

        } catch (SQLException e) {
            logger.logError(e, "Exception during user retrieving");
            return null;
        }
    }

    /**
     * This method create and return a connection
     * @return Connection
     * @throws SQLException - triggered when application is not able to connect to database
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.DATABASE_HOST, DatabaseConfig.DATABASE_USERNAME, DatabaseConfig.DATABASE_PASSWORD);
    }
}
