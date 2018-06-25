package com.notification.client.components.entities;

import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

public class SendMailDto {

    private String[] to;
    private String text;
    private String subject;
    private Integer userId;

    public SendMailDto() {}

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

    /**
     * Filling User object using data from list of cells, and return it.
     * @param cells - list of cells
     * @return SendMailDto object
     */
    public static SendMailDto getMail(List<Cell> cells) {
        if(cells == null || cells.isEmpty()) {
            return null;
        }

        SendMailDto mail = new SendMailDto();
        mail.setSubject(cells.get(0).getStringCellValue());
        mail.setText(cells.get(1).getStringCellValue());
        return mail;
    }
}
