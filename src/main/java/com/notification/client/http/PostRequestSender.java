package com.notification.client.http;

import com.notification.client.components.entities.Message;
import com.notification.client.components.entities.SendMailDto;
import com.notification.client.components.entities.User;
import com.notification.client.services.LoggerServiceImpl;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.client.RestTemplate;

<<<<<<< HEAD
<<<<<<< HEAD
import java.util.List;

=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
public class PostRequestSender {

    private static final LoggerServiceImpl logger = new LoggerServiceImpl();

    private HttpClient httpClient;
    private RestTemplate restTemplate;

    public PostRequestSender() {
        httpClient = HttpClients.createDefault();
        restTemplate = new RestTemplate();
    }

    // User
    public User sendUser(String url, User user) {
        User returnedUser = restTemplate.postForObject(url, user, User.class);
        return returnedUser;
    }

    public User checkUser(String url, User user) {
        User response = restTemplate.postForObject(url, user, User.class);
        return response;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    // Message
=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
    public Message sendMessage(String url, Message message) {
        Message returnedMessage = restTemplate.postForObject(url, message, Message.class);
        return returnedMessage;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public boolean addToBlackList(String url, List<String> emails) {
        restTemplate.postForObject(url, emails, void.class);
        return true;
    }

    public boolean removeFromBlackList(String url, List<String> emails) {
        restTemplate.postForObject(url, emails, void.class);
        return true;
    }

=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
=======
>>>>>>> 07c09e9621bce672c8e32af382c42ccf2dc685d4
    // SendMessage
    public void sendMailDto(String url, SendMailDto mail) {
        // TODO Finish this method. This is not finished version.
        restTemplate.postForObject(url, mail, String.class);
    }
}
