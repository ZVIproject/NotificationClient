package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.entity.SendMailEntity;
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
    public SimpleMailMessage sendSimpleMessage(@RequestBody SendMailEntity sendMailEntity,
                                               @PathVariable("sending_server") String sendingServer) {

        return dispatcher.get(sendingServer).sendSimpleMessage(sendMailEntity);
    }

    @PostMapping(value = "/template")
    public SimpleMailMessage sendTemplateMessage(@RequestBody SendMailEntity sendMailEntity,
                                                 @PathVariable("sending_server") String sendingServer) {

        return dispatcher.get(sendingServer).sendTemplateMessage(sendMailEntity);
    }



}
