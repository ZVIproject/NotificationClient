package com.studnnet.notification_system.component.entity;

import com.studnnet.notification_system.utils.enums.MailStatus;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "resend_message", schema = "notification_system")
public class ResendEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;


    @Column(name = "subject")
    private String subject;

    @Column(name = "text")
    private String text;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private MailStatus status = MailStatus.NEW;

    @Column(name = "create_date", insertable = false, updatable = false)
    @UpdateTimestamp
    private Date created;

    @Column(name = "modified_date")
    @UpdateTimestamp
    private Date modified;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;



    @Override public String toString() {
        return "ToStringExample(" + this.getId() + ", " + this.getEmail()+ ", " + this.getStatus()+ ", " + this.getText() + ")";
    }

    public Integer getId() {
        return id;
    }

    public ResendEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public ResendEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public MailStatus getStatus() {
        return status;
    }

    public ResendEntity setStatus(MailStatus status) {
        this.status = status;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public ResendEntity setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getModified() {
        return modified;
    }

    public ResendEntity setModified(Date modified) {
        this.modified = modified;
        return this;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public ResendEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public ResendEntity setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getText() {
        return text;
    }

    public ResendEntity setText(String text) {
        this.text = text;
        return this;
    }
}
