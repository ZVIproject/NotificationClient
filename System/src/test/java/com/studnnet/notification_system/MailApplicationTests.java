package com.studnnet.notification_system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@PropertySource("classpath:application-gmail_mail.properties")
@ActiveProfiles("test")
public class MailApplicationTests {


	@Test
	public void contextLoads() {
	}



}
