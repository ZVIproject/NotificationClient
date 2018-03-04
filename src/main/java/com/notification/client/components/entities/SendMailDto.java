package com.notification.client.components.entities;

public class SendMailDto {

    private String[] to;
    private String text;
    private String subject;
    private Integer userId;

    public SendMailDto() {}

    public SendMailDto(String[] to, String text, String subject, Integer userId) {
        this.to = to;
        this.text = text;
        this.subject = subject;
        this.userId = userId;
    }

    public String[] getTo() {
        return to;
    }

    public void setTo(String[] to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
