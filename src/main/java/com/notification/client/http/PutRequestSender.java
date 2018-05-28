package com.notification.client.http;

import com.notification.client.components.entities.Message;
import com.notification.client.components.entities.User;
import com.notification.client.services.LoggerServiceImpl;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class PutRequestSender {

    private RestTemplate restTemplate;


    public PutRequestSender() {
        restTemplate = new RestTemplate();
    }

    // User
    public User sendUser(String url, User user) {
        ResponseEntity<User> returnedUser = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(user),
                User.class
        );
        return returnedUser.getBody();
    }

    // Message
    public Message sendMessage(String url, Message message) {
        ResponseEntity<Message> returnedMessage = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(message),
                Message.class
        );
        return returnedMessage.getBody();
    }

    public List<Message> sendMessages(String url, List<Message> messages) {
        ResponseEntity<Message[]> returnedMessages = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(messages),
                Message[].class
        );
        return Arrays.asList(returnedMessages.getBody());
    }

}
