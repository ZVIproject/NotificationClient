package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.entity.SendMailEntity;
import com.studnnet.notification_system.interfacee.MailSendService;
import com.studnnet.notification_system.service.MailSendServiceDispatcher;
import com.studnnet.notification_system.utils.Const;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SendControllerTest {

    @MockBean
    private MailSendServiceDispatcher dispatcher;

    @MockBean
    private MailSendService mailSendService;

    @Autowired
    private MockMvc mockMvc;

    private SendMailEntity sendMailEntity;

    private static String createMailInJson(SendMailEntity sendMailEntity) {
        return "{ \"to\": \"" + sendMailEntity.getTo() + "\", " +
            "\"subject\":\"" + sendMailEntity.getSubject() + "\"," +
            "\"text\":\"" + sendMailEntity.getText() + "\"}";
    }

    @Before
    public void initialization() {
        final String[] receiversEmail = {"zarovni03@gmail.com"};
        final String emailText = "Hello world!!!";
        final String emailSubject = "test";

        this.sendMailEntity = new SendMailEntity(receiversEmail, emailText, emailSubject);
    }

    @Test
    public void sendSimpleMessage() throws Exception {
        doReturn(mailSendService).when(dispatcher).get(Const.GMAIL);
        doReturn(new SimpleMailMessage()).when(mailSendService).sendSimpleMessage(sendMailEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/v1/mail/gmail/simple")
            .accept("application/json")
            .contentType("application/json").content(createMailInJson(sendMailEntity)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void sendTemplateMessage() throws Exception {
        doReturn(mailSendService).when(dispatcher).get(Const.GMAIL);
        doReturn(new SimpleMailMessage()).when(mailSendService).sendSimpleMessage(sendMailEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/v1/mail/gmail/template")
            .accept("application/json")
            .contentType("application/json").content(createMailInJson(sendMailEntity)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}