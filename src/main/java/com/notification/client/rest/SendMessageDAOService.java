package com.notification.client.rest;

import com.notification.client.components.entities.SendMailDto;
import com.notification.client.configs.Urls;
import com.notification.client.http.PostRequestSender;

public class SendMessageDAOService {

    private PostRequestSender postRequestSender;

    public SendMessageDAOService() {
        this.postRequestSender = new PostRequestSender();
    }

    public void sendSimpleMessage(SendMailDto mailDto, String server) {
        postRequestSender.sendMailDto(Urls.SEND_CONTROLLER + server + "/simple", mailDto);
    }

    public void sendMessageFromTemplate(SendMailDto mailDto, String server) {
        postRequestSender.sendMailDto(Urls.SEND_CONTROLLER + server + "/template", mailDto);
    }
}