package com.notification.client.http;

import com.notification.client.components.entities.Message;
import com.notification.client.components.entities.SendMailDto;
import com.notification.client.components.entities.User;
import com.notification.client.services.LoggerServiceImpl;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.client.RestTemplate;

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

    // SendMessage
    public void sendMailDto(String url, SendMailDto mail) {
        // TODO Finish this method. This is not finished version.
        restTemplate.postForObject(url, mail, String.class);
    }
}
