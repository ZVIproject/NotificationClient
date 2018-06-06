package com.studnnet.notification_system.service;

import com.studnnet.notification_system.component.entity.MessageEntity;
import com.studnnet.notification_system.component.entity.ResendEntity;
import com.studnnet.notification_system.component.repositories.MessageRepository;
import com.studnnet.notification_system.component.repositories.ResendRepository;
import com.studnnet.notification_system.interfacee.ResendService;
import com.studnnet.notification_system.utils.abstracts.AbstractMailSendService;
import com.studnnet.notification_system.utils.enums.MailStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class ResendServiceImpl extends AbstractMailSendService implements ResendService {

    private static final Logger LOGGER = Logger.getLogger(AbstractMailSendService.class);

    @Autowired private ResendRepository resendRepository;
    @Autowired private MessageRepository messageRepository;

    @Override
    @Scheduled(fixedRateString = "${scheduled.resend.rate}")
    public void doSend() {

        logInfo("Start scheduled resend.");
        List<ResendEntity> resendEntities = resendRepository.findAllResendMessages(MailStatus.NEW);

        resendEntities.forEach(resendMessage -> {

            final SimpleMailMessage message = initMessageForSend(resendMessage);

            try {

                mailSender.send(message);

                saveSendedMessage(resendMessage, MailStatus.SENDED);

            } catch (MailException e) {

                saveSendedMessage(resendMessage, MailStatus.BLACK_LIST);
                resendRepository.save(resendMessage.setStatus(MailStatus.FAIL));
            }


        });
    }

    private SimpleMailMessage initMessageForSend(ResendEntity resendMessage) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(resendMessage.getEmail());
        message.setText(resendMessage.getText());
        message.setText(resendMessage.getSubject());

        return message;
    }

    private void saveSendedMessage(ResendEntity resendMessage, MailStatus mailStatus) {
        messageRepository.save(new MessageEntity()
            .setStatus(mailStatus)
            .setEmail(resendMessage.getEmail())
            .setUserEntity(resendMessage.getUserEntity())
        );
    }

    private void logInfo(String message) {
        LOGGER.info(message);
    }

    @Override
    public String name() {
        return this.getClass().toString();
    }
}
