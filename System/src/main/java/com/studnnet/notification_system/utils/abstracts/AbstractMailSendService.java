package com.studnnet.notification_system.utils.abstracts;

import com.studnnet.notification_system.component.dto.SendMailDto;
import com.studnnet.notification_system.component.entity.MessageEntity;
import com.studnnet.notification_system.component.entity.ResendEntity;
import com.studnnet.notification_system.component.repositories.MessageRepository;
import com.studnnet.notification_system.component.repositories.ResendRepository;
import com.studnnet.notification_system.component.repositories.StatisticRepository;
import com.studnnet.notification_system.component.repositories.UserRepository;
import com.studnnet.notification_system.configuration.EmailProperties;
import com.studnnet.notification_system.interfacee.MailSendService;
import com.studnnet.notification_system.utils.Const;
import com.studnnet.notification_system.utils.enums.MailStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
//@PropertySource(value = "classpath:application.properties")
public abstract class AbstractMailSendService implements MailSendService, Runnable {

    private static final Logger LOGGER = Logger.getLogger(AbstractMailSendService.class);

    protected JavaMailSender mailSender;

    @Autowired protected EmailProperties emailProperties;
    @Autowired private MessageRepository messageRepository;
    @Autowired private ResendRepository resendRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private StatisticRepository statisticRepository;
    @Autowired private EntityManager entityManager;

    @Value("${threads.count}")
    private int threadsCount;

    private int threadElementsCount;

    private int currentEmailArrayPosition;

    private SendMailDto sendMailDto;
    private int sendCount ;
    private int failedCount ;


    public void doSend(SendMailDto sendMailDto) {
        sendCount =0 ;
        failedCount = 0;
        currentEmailArrayPosition = 0;

        this.sendMailDto = sendMailDto;
        threadElementsCount = (int) Math.ceil((double) (sendMailDto.getTo().length) / threadsCount);

        final ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);

        for (int i = 0; i < threadsCount; i++) {
            executorService.submit(this);
        }

        waitForFinishThreads(executorService);
    }

    private void waitForFinishThreads(ExecutorService executorService) {
        try {

            executorService.shutdown();
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            loggingError("Do send", e);
        }
    }

    @Override
    public void run() {
        if (isSimpleMessage()) {
//            sendSimpleMessage(sendMailDto);

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            prepareMessage(sendMailDto, simpleMailMessage);
        } else {
            sendTemplateMessage(sendMailDto);
        }
    }

    private boolean isSimpleMessage() {
        return sendMailDto.getText() != null;
    }

    @Override
    public SimpleMailMessage sendTemplateMessage(SendMailDto sendMailDto) {
        sendMailDto.setText(
            String.format(Const.TEMPLATE_TEXT, sendMailDto.getTo(),
                sendMailDto.getSubject()));

        return sendSimpleMessage(sendMailDto);
    }


    @Override
    public SimpleMailMessage sendSimpleMessage(SendMailDto sendMailDto) {

        doSend(sendMailDto);

        return null;
    }

    private SimpleMailMessage prepareMessage(SendMailDto sendMailDto, SimpleMailMessage message) {


        String[] messagesForSend = getArrayElements(sendMailDto.getTo());

        for (int i = 0; i < messagesForSend.length; i++) {

            String emailForSend = messagesForSend[i];

            MessageEntity storedMessage = messageRepository.findByEmail(emailForSend);

            if(storedMessage == null){
                MessageEntity messageEntity = new MessageEntity();

                messageEntity.setEmail(emailForSend);
                messageEntity.setSendCount(1);
                messageEntity.setStatus(MailStatus.NEW);
                messageEntity.setUserEntity(
                    userRepository.findOne(
                        sendMailDto.getUserId()));

                storedMessage = messageRepository.save(messageEntity);

            } else {
              if(storedMessage.getStatus() == MailStatus.BLACK_LIST){
                  continue;
              }
            }

            doNormalSendMessage(message, storedMessage, emailForSend);




            }

       // addSendCount();

        return message;
    }

    private void doNormalSendMessage(SimpleMailMessage message, MessageEntity storedMessage, String emailForSend) {
        try {
            message.setTo(emailForSend);
            message.setSubject(sendMailDto.getSubject());
            message.setText(sendMailDto.getText());

            mailSender.send(message);


            storedMessage.setStatus(MailStatus.SENDED);
            storedMessage.setSendCount(storedMessage.getSendCount() + 1);

            messageRepository.save(storedMessage);


            sendCount++;

        } catch (MailException e) {
            loggingError("fail to send message to " + emailForSend, e);

            loggingSentEmails(message.getTo());

            if(resendRepository.findByEmail(emailForSend) != null){
                return;
            }

            resendRepository.save(new ResendEntity()
                .setEmail(emailForSend)
                .setStatus(MailStatus.NEW)
                .setUserEntity(
                    userRepository.findOne(sendMailDto.getUserId())
                )
                .setText(sendMailDto.getText())
                .setSubject(sendMailDto.getSubject())
            );
        }

    }

    public void addSendCount(){
        statisticRepository.addSendCountByEmail(sendCount);
        statisticRepository.addFailedCountByEmail(failedCount);
    }


    private synchronized String[] getArrayElements(String[] array){

        return Arrays.copyOfRange(array, currentEmailArrayPosition,
                currentEmailArrayPosition += threadElementsCount);
    }

    private void loggingSentEmails(String[] emails){
        Arrays.stream(emails).forEach(
            email -> LOGGER.info("Sent mail to : " + email));

    }

    private void loggingError(String errorPlace, Exception e) {
        LOGGER.info(String.format(":********************************: %s \n %s", errorPlace, e.getMessage()));
    }

}
