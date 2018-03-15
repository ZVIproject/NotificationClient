package com.studnnet.notification_system.component.repositories;

import com.studnnet.notification_system.component.entity.MessageEntity;
import com.studnnet.notification_system.utils.enums.MailStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.lang.annotation.Native;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer>{
    MessageEntity findByEmail(String emailForSend);

    @Modifying
    @Query("UPDATE MessageEntity me SET me.status = :status WHERE me.email IN (:emails)")
    void updateMailStatus(@Param("emails") List<String> emails, @Param("status") MailStatus status);

    @Query("SELECT me FROM MessageEntity me WHERE me.status <> :status")
    List<MessageEntity> findAllWithoutBlackList(@Param("status") MailStatus status);

    List<MessageEntity> findByStatus(MailStatus blackList);

    @Query("SELECT me FROM MessageEntity me WHERE me.status <> :status AND me.modified < :to AND me.modified > :fromm")
    List<MessageEntity> findByDateFromTo(@Param("status") MailStatus status,
                                         @Param("fromm") Date fromm,
                                         @Param("to") Date to);

    @Query(value = "select * from message where MONTH(modified_date) = :date AND status <> :status", nativeQuery = true)
    List<MessageEntity> findTop(@Param("status") MailStatus blackList,
                                @Param("date") Integer month);
}
