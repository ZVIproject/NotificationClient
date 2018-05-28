package com.notification.client.http;

import com.notification.client.components.entities.Message;
import com.notification.client.components.entities.SendMailDto;
import com.notification.client.components.entities.User;
import org.springframework.web.client.RestTemplate;

import java.util.List;


public class PostRequestSender {

    private RestTemplate restTemplate;


    public PostRequestSender() {
        restTemplate = new RestTemplate();
    }

    // User
    public User sendUser(String url, User user) {
        return restTemplate.postForObject(url, user, User.class);
    }

    public User checkUser(String url, User user) {
        return restTemplate.postForObject(url, user, User.class);
    }

    // Message
    public Message sendMessage(String url, Message message) {
        return restTemplate.postForObject(url, message, Message.class);
    }

    public boolean addToBlackList(String url, List<String> emails) {
        restTemplate.postForObject(url, emails, void.class);
        return true;
    }

    public boolean removeFromBlackList(String url, List<String> emails) {
        restTemplate.postForObject(url, emails, void.class);
        return true;
    }

    // SendMessage
    public void sendMailDto(String url, SendMailDto mail) {
        // TODO Finish this method. This is not finished version.
        restTemplate.postForObject(url, mail, String.class);
    }
}
