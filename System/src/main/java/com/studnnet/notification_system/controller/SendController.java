package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.dto.SendMailDto;
import com.studnnet.notification_system.service.MailSendServiceDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/v1/mail/{sending_server}")
public class SendController {

    @Autowired
    protected MailSendServiceDispatcher dispatcher;

    @PostMapping(value = "/simple")
    public SimpleMailMessage sendSimpleMessage(@RequestBody SendMailDto sendMailDto,
                                               @PathVariable("sending_server") String sendingServer) {

        return dispatcher.get(sendingServer).sendSimpleMessage(sendMailDto);
    }

    @PostMapping(value = "/template")
    public SimpleMailMessage sendTemplateMessage(@RequestBody SendMailDto sendMailDto,
                                                 @PathVariable("sending_server") String sendingServer) {

        return dispatcher.get(sendingServer).sendTemplateMessage(sendMailDto);
    }



}
