package com.studnnet.notification_system.component.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "statistic", schema = "notification_system")
@Data
public class StatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "send_count")
    private Integer sendCount;

    @Column(name = "failed_count")
    private Integer failedCount;

    @Column(name = "create_date", insertable = false, updatable = false)
    @CreationTimestamp
    private Date created;

    @Column(name = "modified_date")
    @UpdateTimestamp
    private Date modified;

}
