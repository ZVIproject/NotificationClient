package com.studnnet.notification_system.component.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class SendMailEntity {

    private String[] to;

    private String text;

    private String subject;

}
