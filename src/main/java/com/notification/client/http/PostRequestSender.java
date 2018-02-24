package com.notification.client.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.notification.client.components.entities.Message;
import com.notification.client.components.entities.User;
import com.notification.client.services.LoggerServiceImpl;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.execchain.RequestAbortedException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

public class PostRequestSender {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    private HttpClient httpClient;
    private RestTemplate restTemplate;

    public PostRequestSender() {
        httpClient = HttpClients.createDefault();
    }

    // User
    public User sendUser(String url, User user) {
        User returnedUser = restTemplate.postForObject(url, user, User.class);
        return returnedUser;
    }

    public boolean checkUser(String url, User user) {
        boolean response = restTemplate.postForObject(url, user, boolean.class);
        return response;
    }

    public Message sendMessage(String url, Message message) {
        Message returnedMessage = restTemplate.postForObject(url, message, Message.class);
        return returnedMessage;
    }

    // TODO: https://github.com/ZVIproject/NotificationClient/blob/ns_system/System/src/main/java/com/studnnet/notification_system/controller/SendController.java
    // find out the way to make such requests.
}
