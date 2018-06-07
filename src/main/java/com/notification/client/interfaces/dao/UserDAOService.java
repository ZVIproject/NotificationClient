package com.notification.client.interfaces.dao;

import com.notification.client.components.entities.RemoteUser;

import java.util.List;

public interface UserDAOService {

    /**
     * Get all users from remote DB.
     * @return list of users
     */
    List<RemoteUser> getAll();

    /**
     * Get user by its first name and last name.
     * @return user object
     */
    RemoteUser getUserByFirstAndLastNames(String firstName, String lastName);
}
