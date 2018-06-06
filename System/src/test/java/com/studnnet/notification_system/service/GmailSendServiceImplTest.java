package com.studnnet.notification_system.service;

import com.studnnet.notification_system.MailApplication;
import com.studnnet.notification_system.component.dto.SendMailDto;
import com.studnnet.notification_system.component.entity.ResendEntity;
import com.studnnet.notification_system.component.entity.UserEntity;
import com.studnnet.notification_system.component.repositories.ResendRepository;
import com.studnnet.notification_system.component.repositories.UserRepository;
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
@PropertySource(value = "classpath:application-test.properties")
public class GmailSendServiceImplTest {

    @Autowired
    private MailSendServiceDispatcher dispatcher;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResendRepository resendRepository;

    private SendMailDto sendMailDto;

    @Value("${threads.count}")
    private int g;

    @Before
    public void initialization() {
        final String[] receiversEmails = {"zarovniy03mail.com"};
        final String emailText = "Hello world!!!";
        final String emailSubject = "test";


        this.sendMailDto = new SendMailDto(receiversEmails, emailText, emailSubject, createUser().getId());
    }

    @Test
    public void sendMessageFailedIfMessagesWasNotSent() throws Exception {

        dispatcher.get(Const.GMAIL).doSend(sendMailDto);

    }

    @Test
    public void sendSimpleMessageFailedIfMessageDataIsNotEquals(){

        SimpleMailMessage testMailMessage = dispatcher.get(Const.GMAIL).sendSimpleMessage(sendMailDto);

        for (ResendEntity resendEntity : resendRepository.findAll()) {
            System.out.println(resendEntity.toString());
        }

      //  checkResponseMail(testMailMessage, true);
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

    }

    private UserEntity createUser() {
        UserEntity testUser = new UserEntity();
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword");

        return userRepository.save(testUser);
    }

}