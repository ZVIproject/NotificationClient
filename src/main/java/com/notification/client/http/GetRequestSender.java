package com.notification.client.http;

import com.notification.client.components.entities.Message;
import com.notification.client.components.entities.Statistic;
import com.notification.client.components.entities.User;
import com.notification.client.services.LoggerServiceImpl;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
<<<<<<< HEAD
import java.util.Date;
=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
import java.util.List;

public class GetRequestSender {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    private HttpClient httpClient;
    private RestTemplate restTemplate;

    public GetRequestSender() {
        httpClient = HttpClients.createDefault();
        restTemplate = new RestTemplate();
    }

    // User
    public List<User> getUsers(String url) {
        logger.logInfo("Sending GET request to " + url);
        User[] users = restTemplate.getForObject(url, User[].class);
        return Arrays.asList(users);
    }

    public User getUser(String url) {
        logger.logInfo("Sending GET request to " + url);
        User user = restTemplate.getForObject(url, User.class);
        return user;
    }

    // Message
    public List<Message> getMessages(String url) {
        logger.logInfo("Sending GET request to " + url);
        Message[] messages = restTemplate.getForObject(url, Message[].class);
        return Arrays.asList(messages);
    }

    public Message getMessageById(String url) {
        logger.logInfo("Sending GET request to " + url);
        Message message = restTemplate.getForObject(url, Message.class);
        return message;
    }

<<<<<<< HEAD
    public List<Message> top(String url) {
        Message[] messages = restTemplate.getForObject(url, Message[].class);
        return Arrays.asList(messages);
    }

    public List<Message> getBlackListedMessages(String url) {
        Message[] messages = restTemplate.getForObject(url, Message[].class);
        return Arrays.asList(messages);
    }

    public List<Message> getMessagesFromTo(String url) {
        Message[] messages = restTemplate.getForObject(url, Message[].class);
        return Arrays.asList(messages);
    }

=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
    // Statistic
    public List<Statistic> getStatistics(String url) {
        logger.logInfo("Sending GET request to " + url);
        Statistic[] statistics = restTemplate.getForObject(url, Statistic[].class);
        return Arrays.asList(statistics);
    }
}
