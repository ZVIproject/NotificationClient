package com.studnnet.notification_system.component.entity;

import lombok.Data;

import javax.persistence.*;

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

}
