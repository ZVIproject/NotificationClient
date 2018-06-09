package com.notification.client.components.entities;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Receiver {

    private Long id;
    private String name;
    private String group;
    private String email;
    private BooleanProperty isSend = new SimpleBooleanProperty(false);

    public Receiver() {}

    public Receiver(String name, String group, String email) {
        this.name = name;
        this.group = group;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isIsSend() {
        return isSend.get();
    }

    public BooleanProperty isSendProperty() {
        return isSend;
    }

    public void setIsSend(boolean isSend) {
        this.isSend.set(isSend);
    }
}
