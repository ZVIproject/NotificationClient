package com.studnnet.notification_system.component.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "user", schema = "notification_system")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "position")
    private String position;

    @Column(name = "create_date", insertable = false, updatable = false)
    @UpdateTimestamp
    private Date created;

    @Column(name = "modified_date")
    @UpdateTimestamp
    private Date modified;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private Set<MessageEntity> messages;
}
