package com.notification.client.rest;

import com.notification.client.components.entities.Message;
import com.notification.client.configs.Urls;
import com.notification.client.http.GetRequestSender;
import com.notification.client.http.PostRequestSender;
import com.notification.client.http.PutRequestSender;

import java.util.Date;
import java.util.List;

public class MessageDAOService {

    private GetRequestSender getRequestSender;
    private PostRequestSender postRequestSender;
    private PutRequestSender putRequestSender;

    public MessageDAOService() {
        getRequestSender = new GetRequestSender();
        postRequestSender = new PostRequestSender();
        putRequestSender = new PutRequestSender();
    }

    public List<Message> createMessages(List<Message> messages) {
        List<Message> messageList = putRequestSender.sendMessages(Urls.MESSAGE_CONTROLLER + "all", messages);
        return messageList;
    }

    public boolean addToBlackList(List<String> emails) {
        return postRequestSender.addToBlackList(Urls.MESSAGE_CONTROLLER + "blacklist", emails);
    }

    public List<Message> getBlackListedMessages() {
        return getRequestSender.getBlackListedMessages(Urls.MESSAGE_CONTROLLER + "blacklist");
    }

    public List<Message> getMessagesFromTo(long from, long to) {
        return getRequestSender.getMessagesFromTo(Urls.MESSAGE_CONTROLLER + from + "/" + to);
    }

    public boolean removeFromBlackList(List<String> emails) {
        return postRequestSender.removeFromBlackList(Urls.MESSAGE_CONTROLLER + "blacklist/remove", emails);
    }

    public List<Message> top() {
        return getRequestSender.top(Urls.MESSAGE_CONTROLLER + "top");
    }

    public Message createMessage(Message message) {
        Message returnedMessage = putRequestSender.sendMessage(Urls.MESSAGE_CONTROLLER, message);
        return returnedMessage;
    }

    public List<Message> getMessages() {
        List<Message> messages = getRequestSender.getMessages(Urls.MESSAGE_CONTROLLER);
        return messages;
    }

    public Message getMessage(int id) {
        Message message = getRequestSender.getMessageById(Urls.MESSAGE_CONTROLLER + id);
        return message;
    }

    public Message updateMessage(Message message) {
        Message returnedMessage = postRequestSender.sendMessage(Urls.MESSAGE_CONTROLLER, message);
        return returnedMessage;
    }
}
