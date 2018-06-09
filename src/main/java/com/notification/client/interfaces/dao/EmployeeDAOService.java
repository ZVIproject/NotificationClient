package com.notification.client.interfaces.dao;

import com.notification.client.components.entities.Employee;

import java.util.List;

public interface EmployeeDAOService {

    /**
     * Get user by its first name and last name.
     * @return user object
     */
    Employee getUserByFirstAndLastNames(String firstName, String lastName);
}
