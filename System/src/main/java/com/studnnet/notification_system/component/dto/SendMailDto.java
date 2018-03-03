package com.studnnet.notification_system.component.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class SendMailDto {

    private String[] to;

    private String text;

    private String subject;

    private Integer userId;

}
