package com.notification.client.rest;

import com.notification.client.components.entities.User;
import com.notification.client.configs.Urls;
import com.notification.client.http.GetRequestSender;
import com.notification.client.http.PostRequestSender;
import com.notification.client.http.PutRequestSender;

import java.util.List;

public class UserDAOService {

    private GetRequestSender getRequestSender;
    private PostRequestSender postRequestSender;
    private PutRequestSender putRequestSender;

    public UserDAOService() {
        getRequestSender = new GetRequestSender();
        postRequestSender = new PostRequestSender();
        putRequestSender = new PutRequestSender();
    }

    public List<User> getUsers() {
        List<User> users = getRequestSender.getUsers(Urls.USER_CONTROLLER);
        return users;
    }

    public User getUser(int id) {
        User user = getRequestSender.getUser(Urls.USER_CONTROLLER + id);
        return user;
    }

    public User createUser(User user) {
        User returnedUser = putRequestSender.sendUser(Urls.USER_CONTROLLER, user);
        return returnedUser;
    }

    public User updateUser(User user) {
        User returnedUser = postRequestSender.sendUser(Urls.USER_CONTROLLER, user);
        return returnedUser;
    }

    public User checkUser(User user) {
        return postRequestSender.checkUser(Urls.USER_CONTROLLER + "authorization", user);
    }
}
