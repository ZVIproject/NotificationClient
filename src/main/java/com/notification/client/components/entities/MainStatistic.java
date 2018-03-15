package com.notification.client.components.entities;

public class MainStatistic {

    private String name;
    private String status;
    private Integer sendCount;
    private String created;
    private String email;

    public MainStatistic() {}

    public MainStatistic(String name, String status, Integer sendCount, String created, String email) {
        this.name = name;
        this.status = status;
        this.sendCount = sendCount;
        this.created = created;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
