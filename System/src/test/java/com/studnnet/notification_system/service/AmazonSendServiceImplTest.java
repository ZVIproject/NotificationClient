package com.studnnet.notification_system.service;

import com.studnnet.notification_system.MailApplication;
import com.studnnet.notification_system.component.dto.SendMailDto;
import com.studnnet.notification_system.utils.Const;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@Deprecated
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MailApplication.class)
@Ignore
public class AmazonSendServiceImplTest {

    @Autowired
    private MailSendServiceDispatcher dispatcher;

    private SendMailDto sendMailDto;


    @Before
    public void initialization() {
        final String[] receiversEmail = {"zarovni03@gmail.com"};
        final String emailText = "Hello amazon!!!";
        final String emailSubject = "test";

        this.sendMailDto = new SendMailDto(receiversEmail, emailText, emailSubject, 1);
    }

    @Test
    public void sendSimpleMessageFailedIfMessageDataIsNotEquals() throws Exception {

        SimpleMailMessage testMailMessage = dispatcher.get(Const.AMAZON).sendSimpleMessage(sendMailDto);

        checkResponseMail(testMailMessage, true);
    }

    @Test
    public void sendTemplateMessageFailedIfMessageDataIsNotEquals() throws Exception {
        SimpleMailMessage testMailMessage = dispatcher.get(Const.AMAZON).sendTemplateMessage(sendMailDto);

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