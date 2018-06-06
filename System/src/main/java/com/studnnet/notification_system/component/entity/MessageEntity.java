package com.studnnet.notification_system.component.entity;

import com.studnnet.notification_system.utils.enums.MailStatus;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message", schema = "notification_system")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;


    @Column(name = "send_count")
    private Integer sendCount;

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


    public Integer getId() {
        return id;
    }

    public MessageEntity setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public MessageEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public MessageEntity setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
        return this;
    }

    public MailStatus getStatus() {
        return status;
    }

    public MessageEntity setStatus(MailStatus status) {
        this.status = status;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public MessageEntity setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getModified() {
        return modified;
    }

    public MessageEntity setModified(Date modified) {
        this.modified = modified;
        return this;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public MessageEntity setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
        return this;
    }
}
