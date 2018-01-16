package com.studnnet.notification_system.interfacee;

import com.studnnet.notification_system.component.entity.MailEntity;
import org.springframework.mail.SimpleMailMessage;

public interface MailSendService {

    SimpleMailMessage sendSimpleMessage(MailEntity mailEntity);

    SimpleMailMessage sendTemplateMessage(MailEntity mailEntity);

    String name();
}
