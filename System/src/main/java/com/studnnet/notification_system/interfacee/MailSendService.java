package com.studnnet.notification_system.interfacee;

import com.studnnet.notification_system.component.entity.SendMailEntity;
import org.springframework.mail.SimpleMailMessage;

public interface MailSendService {

    void doSend(SendMailEntity sendMailEntity);

    SimpleMailMessage sendSimpleMessage(SendMailEntity sendMailEntity);

    SimpleMailMessage sendTemplateMessage(SendMailEntity sendMailEntity);

    String name();
}
