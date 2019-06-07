package com.studnnet.notification_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.studnnet.notification_system")
@EnableAutoConfiguration
public class MailApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailApplication.class, args);
	}
}
