package com.studnnet.notification_system.component.repositories;


import com.studnnet.notification_system.component.entity.ResendEntity;
import com.studnnet.notification_system.utils.enums.MailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResendRepository extends JpaRepository<ResendEntity, Integer> {

    @Query("SELECT re FROM ResendEntity re WHERE re.status = :status")
    List<ResendEntity> findAllResendMessages(@Param("status") MailStatus status);

    @Query("SELECT re FROM ResendEntity re WHERE re.email = :email")
    ResendEntity findByEmail(@Param("email") String email);
}
