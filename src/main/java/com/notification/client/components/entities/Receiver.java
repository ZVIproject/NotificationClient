package com.notification.client.components.entities;

public class Receiver {

    private String name;
    private String group;
    private String email;

    public Receiver() {}

    public Receiver(String name, String group, String email) {
        this.name = name;
        this.group = group;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
