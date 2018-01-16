package com.studnnet.notification_system.component.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MailEntity {

    private String to;

    private String text;

    private String subject;

}
