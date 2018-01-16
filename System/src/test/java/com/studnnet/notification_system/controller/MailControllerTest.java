package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.entity.MailEntity;
import com.studnnet.notification_system.interfacee.MailSendService;
import com.studnnet.notification_system.service.MailSendServiceDispatcher;
import com.studnnet.notification_system.utils.Const;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class MailControllerTest {

    @MockBean
    private MailSendServiceDispatcher dispatcher;

    @MockBean
    @Qualifier("gmailSendServiceImpl")
    private MailSendService mailSendService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MailController mailController;

    private MailEntity mailEntity;

    private static String createMailInJson(MailEntity mailEntity) {
        return "{ \"to\": \"" + mailEntity.getTo() + "\", " +
            "\"subject\":\"" + mailEntity.getSubject() + "\"," +
            "\"text\":\"" + mailEntity.getText() + "\"}";
    }

    @Before
    public void initialization() {
        final String receiversEmail = "yuri.gavrysh@gmail.com";
        final String emailText = "Hello world!!!";
        final String emailSubject = "test";

        this.mailEntity = new MailEntity(receiversEmail, emailText, emailSubject);
    }

    @Test
    public void sendSimpleMessage() throws Exception {
        doReturn(mailSendService).when(dispatcher).get(Const.GMAIL);
        doReturn(new SimpleMailMessage()).when(mailSendService).sendSimpleMessage(mailEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/v1/mail/gmail/simple")
            .accept("application/json")
            .contentType("application/json").content(createMailInJson(mailEntity)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void sendTemplateMessage() throws Exception {
        doReturn(mailSendService).when(dispatcher).get(Const.GMAIL);
        doReturn(new SimpleMailMessage()).when(mailSendService).sendSimpleMessage(mailEntity);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/v1/mail/gmail/template")
            .accept("application/json")
            .contentType("application/json").content(createMailInJson(mailEntity)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}