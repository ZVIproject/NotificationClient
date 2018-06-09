package com.notification.client.services.dao;

import com.notification.client.components.entities.Employee;
import com.notification.client.configs.DatabaseConfig;
import com.notification.client.interfaces.dao.EmployeeDAOService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class EmployeeDAOServiceImpl implements EmployeeDAOService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeDAOServiceImpl.class);

    private static final String QUERY_FIND_BY_FIRST_NAME_AND_LAST_NAME =
            "SELECT id, first_name, middle_name, last_name FROM employee WHERE first_name=?, last_name=?";

    @Override
    public Employee getUserByFirstAndLastNames(String firstName, String lastName) {

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_FIND_BY_FIRST_NAME_AND_LAST_NAME);
            statement.setString(1, firstName);
            statement.setString(2, lastName);

            ResultSet resultSet = statement.executeQuery();

            resultSet.next();

            Employee employee = Employee.getEmployee(resultSet);
            return employee;

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
        return DriverManager.getConnection(
                DatabaseConfig.DATABASE_HOST, DatabaseConfig.DATABASE_USERNAME, DatabaseConfig.DATABASE_PASSWORD);
    }
}
