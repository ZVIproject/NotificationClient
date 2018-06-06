package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.dto.SendMailDto;
import com.studnnet.notification_system.interfacee.MailSendService;
import com.studnnet.notification_system.service.GmailSendServiceImpl;
import com.studnnet.notification_system.service.MailSendServiceDispatcher;
import com.studnnet.notification_system.utils.Const;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
@WebMvcTest
@Ignore
//@ActiveProfiles("test")
public class SendControllerTest {

    @MockBean
    private MailSendServiceDispatcher dispatcher;

    @MockBean
    private GmailSendServiceImpl mailSendService;

    @Autowired
    private MockMvc mockMvc;

    private SendMailDto sendMailDto;

    private static String createMailInJson(SendMailDto sendMailDto) {
        return "{ \"to\": \"" + sendMailDto.getTo() + "\", " +
            "\"subject\":\"" + sendMailDto.getSubject() + "\"," +
            "\"text\":\"" + sendMailDto.getText() + "\"}";
    }

    @Before
    public void initialization() {
        final String[] receiversEmail = {"zarovni03@gmail.com"};
        final String emailText = "Hello world!!!";
        final String emailSubject = "test";

        this.sendMailDto = new SendMailDto(receiversEmail, emailText, emailSubject, 1);
    }

    @Test
    public void sendSimpleMessage() throws Exception {
        doReturn(mailSendService).when(dispatcher).get(Const.GMAIL);
        doReturn(new SimpleMailMessage()).when(mailSendService).sendSimpleMessage(sendMailDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/v1/mail/gmail/simple")
            .accept("application/json")
            .contentType("application/json").content(createMailInJson(sendMailDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void sendTemplateMessage() throws Exception {
        doReturn(mailSendService).when(dispatcher).get(Const.GMAIL);
        doReturn(new SimpleMailMessage()).when(mailSendService).sendSimpleMessage(sendMailDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/rest/v1/mail/gmail/template")
            .accept("application/json")
            .contentType("application/json").content(createMailInJson(sendMailDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

}