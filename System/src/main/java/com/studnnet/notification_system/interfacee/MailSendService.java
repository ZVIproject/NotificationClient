package com.studnnet.notification_system.interfacee;

import com.studnnet.notification_system.component.dto.SendMailDto;
import org.springframework.mail.SimpleMailMessage;

public interface MailSendService {

    void doSend(SendMailDto sendMailDto);

    SimpleMailMessage sendSimpleMessage(SendMailDto sendMailDto);

    SimpleMailMessage sendTemplateMessage(SendMailDto sendMailDto);

    String name();
}
