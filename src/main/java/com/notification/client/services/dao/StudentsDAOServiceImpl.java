package com.notification.client.services.dao;

import com.notification.client.components.entities.Receiver;
import com.notification.client.components.entities.User;
import com.notification.client.configs.DatabaseConfig;
import com.notification.client.interfaces.dao.StudentsDAOService;
import com.notification.client.services.LoggerServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentsDAOServiceImpl implements StudentsDAOService {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    private static final String QUERY_GET_STUDENTS_IDS_AND_TITLE =
            "SELECT sg.`student_id` AS student_id, g.`title` AS title FROM `student_group` AS sg, `group` AS g WHERE `group_id` " +
            "IN (SELECT `id` FROM `group` WHERE `curator_id` = (SELECT `id` FROM `employee` WHERE `first_name` = ? AND `last_name` = ?)) " +
            "AND sg.`group_id` = g.`id`";
    private static final String QUERY_GET_STUDENTS_INFO =
            "SELECT se.`email` AS email, s.`first_name` AS first_name, s.`last_name` AS last_name, s.`middle_name` AS middle_name " +
            "FROM `students_emails` AS se INNER JOIN `student` AS s ON se.`student_id` = s.`id` WHERE se.`student_id` = ?;";

    @Override
    public List<Receiver> getStudentsOfGivenTeacher(User user) {

        List<Receiver> receivers = getStudentsIds(user);
        if (receivers == null) {
            return null;
        }

        try (Connection connection = getConnection()) {
            for (Receiver receiver : receivers) {
                PreparedStatement statement = connection.prepareStatement(QUERY_GET_STUDENTS_INFO);
                statement.setLong(1, receiver.getId());

                ResultSet resultSet = statement.executeQuery();

                resultSet.next();

                receiver.setEmail(resultSet.getString("email"));
                receiver.setName(resultSet.getString("first_name") + " " +
                        resultSet.getString("last_name") + " " +
                        resultSet.getString("middle_name"));
            }

            return receivers;

        } catch (SQLException e) {
            logger.logError(e, "Error during students retrieving");
            return null;
        }
    }

    private List<Receiver> getStudentsIds(User user) {

        try (Connection connection = getConnection()) {
            PreparedStatement statement = connection.prepareStatement(QUERY_GET_STUDENTS_IDS_AND_TITLE);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());

            ResultSet resultSet = statement.executeQuery();

            List<Receiver> receiverList = new ArrayList<>();
            while (resultSet.next()) {
                Receiver receiver = new Receiver();
                receiver.setId(resultSet.getLong("student_id"));
                receiver.setGroup(resultSet.getString("title"));
                receiverList.add(receiver);
            }

            return receiverList;

        } catch (SQLException e) {
            logger.logError(e, "Error during student ids retrieving");
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
