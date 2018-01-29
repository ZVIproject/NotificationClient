package com.studnnet.notification_system.component.entity;

import com.studnnet.notification_system.utils.enums.MailStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message", schema = "notification_system")
@Data
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
    private MailStatus status = MailStatus.NEW;

    @Column(name = "create_date", insertable = false, updatable = false)
    @CreationTimestamp
    private Date created;

    @Column(name = "modified_date")
    @UpdateTimestamp
    private Date modified;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
