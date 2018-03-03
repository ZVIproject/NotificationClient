package com.studnnet.notification_system.service;

import com.studnnet.notification_system.MailApplication;
import com.studnnet.notification_system.component.dto.SendMailDto;
import com.studnnet.notification_system.utils.Const;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MailApplication.class)
@PropertySource(value = "classpath:test.properties")
public class GmailSendServiceImplTest {

    @Autowired
    private MailSendServiceDispatcher dispatcher;

    private SendMailDto sendMailDto;

    @Value("${threads.count}")
    private int g;

    @Before
    public void initialization() {
        final String[] receiversEmails = {"zarovniy16@mail.ru", "zarovni03@gmail.com", "zarovniy15@mail.ru", "zarovniy229@mail.ru"};
        final String emailText = "Hello world!!!";
        final String emailSubject = "test";

        this.sendMailDto = new SendMailDto(receiversEmails, emailText, emailSubject, 1);
    }

    @Test
    public void sendMessageFailedIfMessagesWasNotSent() throws Exception {

        dispatcher.get(Const.GMAIL).doSend(sendMailDto);

    }

    @Test
    public void sendSimpleMessageFailedIfMessageDataIsNotEquals() throws Exception {

        SimpleMailMessage testMailMessage = dispatcher.get(Const.GMAIL).sendSimpleMessage(sendMailDto);

        checkResponseMail(testMailMessage, true);
    }

    @Test
    public void sendTemplateMessageFailedIfMessageDataIsNotEquals() throws Exception {
        SimpleMailMessage testMailMessage = dispatcher.get(Const.GMAIL).sendTemplateMessage(sendMailDto);

        checkResponseMail(testMailMessage, false);

    }

    private void checkResponseMail(SimpleMailMessage testMailMessage, boolean isNotTemplateMessage) {
        if (isNotTemplateMessage) {
            assertEquals("Text of message should be equal", testMailMessage.getText(), sendMailDto.getText());
        }

        assertEquals("Receiver's email should be equal", testMailMessage.getTo()[0], sendMailDto.getTo());
        assertEquals("The subject text should be equal", testMailMessage.getSubject(), sendMailDto.getSubject());
    }

}