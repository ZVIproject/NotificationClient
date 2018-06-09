package com.notification.client.interfaces.dao;

import com.notification.client.components.entities.Receiver;
import com.notification.client.components.entities.User;

import java.util.List;

public interface StudentsDAOService {

    /**
     * Select students of a given teacher.
     */
    List<Receiver> getStudentsOfGivenTeacher(User user);
}
