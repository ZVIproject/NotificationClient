package com.studnnet.notification_system.component.entity;

import com.studnnet.notification_system.utils.enums.MailStatus;
import lombok.Data;

import javax.persistence.*;

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
    private MailStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

}
