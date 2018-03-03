package com.studnnet.notification_system.service;

import com.studnnet.notification_system.configuration.EmailProperties;
import com.studnnet.notification_system.utils.Const;
import com.studnnet.notification_system.utils.abstracts.AbstractMailSendService;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Properties;

@Service
public class GmailSendServiceImpl extends AbstractMailSendService {

    @PostConstruct
    public void postConstruct() {
        config(emailProperties.getGmail());
    }

    private void config(EmailProperties.ConnectGmail connect) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(connect.getHost());
        mailSender.setPort(connect.getPort());

        mailSender.setUsername(connect.getUsername());
        mailSender.setPassword(connect.getPassword());

        Properties props = mailSender.getJavaMailProperties();

        props.put("mail.smtp.auth", connect.getProperties().getSmtpAuth());
        props.put("mail.smtp.starttls.enable", connect.getProperties().getTlsEnable());

        this.mailSender = mailSender;
    }

    @Override
    public String name() {
        return Const.GMAIL;
    }

}
