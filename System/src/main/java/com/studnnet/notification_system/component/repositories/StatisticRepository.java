package com.studnnet.notification_system.component.repositories;

import com.studnnet.notification_system.component.entity.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Integer>{
    @Modifying
    @Query("UPDATE StatisticEntity se SET se.sendCount = se.sendCount + 1")
    void addSendCountByEmail();

    @Modifying
    @Query("UPDATE StatisticEntity se SET se.failedCount = se.failedCount + 1")
    void addFailedCountByEmail();
}
