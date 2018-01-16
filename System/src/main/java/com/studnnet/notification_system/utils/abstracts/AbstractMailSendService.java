package com.studnnet.notification_system.utils.abstracts;

import com.studnnet.notification_system.component.entity.MailEntity;
import com.studnnet.notification_system.interfacee.MailSendService;
import com.studnnet.notification_system.configuration.EmaiProperties;
import com.studnnet.notification_system.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public abstract class AbstractMailSendService implements MailSendService {

    @Autowired
    protected EmaiProperties emaiProperties;

    protected JavaMailSender mailSender;

    @Override
    public SimpleMailMessage sendSimpleMessage(MailEntity mailEntity) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailEntity.getTo());
        message.setSubject(mailEntity.getSubject());
        message.setText(mailEntity.getText());
        mailSender.send(message);

        return message;
    }


    @Override
    public SimpleMailMessage sendTemplateMessage(MailEntity mailEntity) {
        mailEntity.setText(
            String.format(Const.TEMPLATE_TEXT, mailEntity.getTo(),
                mailEntity.getSubject()));

        return sendSimpleMessage(mailEntity);
    }
}
