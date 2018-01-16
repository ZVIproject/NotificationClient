package com.studnnet.notification_system.component.repositories;

import com.studnnet.notification_system.component.entity.StatisticEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Integer>{
}
