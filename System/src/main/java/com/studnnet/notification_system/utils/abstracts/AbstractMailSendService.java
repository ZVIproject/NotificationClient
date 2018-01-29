package com.studnnet.notification_system.utils.abstracts;

import com.studnnet.notification_system.component.entity.SendMailEntity;
import com.studnnet.notification_system.configuration.EmailProperties;
import com.studnnet.notification_system.interfacee.MailSendService;
import com.studnnet.notification_system.utils.Const;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@PropertySource(value = "classpath:application.properties")
public abstract class AbstractMailSendService implements MailSendService, Runnable {

    private static final Logger LOGGER = Logger.getLogger(AbstractMailSendService.class);

    @Autowired
    protected EmailProperties emailProperties;

    protected JavaMailSender mailSender;

    @Value("${threads.count}")
    private int threadsCount;

    private int threadElementsCount;

    private int currentEmailArrayPosition = 0;

    private SendMailEntity sendMailEntity;

    public void doSend(SendMailEntity sendMailEntity) {
        this.sendMailEntity = sendMailEntity;
        threadElementsCount = (int) Math.ceil((double)sendMailEntity.getTo().length / threadsCount);

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
            sendSimpleMessage(sendMailEntity);
        } else {
            sendTemplateMessage(sendMailEntity);
        }
    }

    private boolean isSimpleMessage() {
        return sendMailEntity.getText() != null;
    }

    @Override
    public SimpleMailMessage sendTemplateMessage(SendMailEntity sendMailEntity) {
        sendMailEntity.setText(
            String.format(Const.TEMPLATE_TEXT, sendMailEntity.getTo(),
                sendMailEntity.getSubject()));

        return sendSimpleMessage(sendMailEntity);
    }

    @Override
    public SimpleMailMessage sendSimpleMessage(SendMailEntity sendMailEntity) {

        SimpleMailMessage message = new SimpleMailMessage();

        mailSender.send(prepareMessage(message));

        loggingSentEmails(message.getTo());

        return message;
    }

    private SimpleMailMessage prepareMessage(SimpleMailMessage message) {

        message.setTo(getArrayElements(sendMailEntity.getTo()));
        message.setSubject(sendMailEntity.getSubject());
        message.setText(sendMailEntity.getText());

        return message;
    }

    private synchronized String[] getArrayElements(String[] array){
          return  Arrays.copyOfRange(array, currentEmailArrayPosition,
                currentEmailArrayPosition += threadElementsCount);
    }

    private void loggingSentEmails(String[] emails){
        Arrays.stream(emails).forEach(
            email -> LOGGER.info("Sent mail to : " + email));

    }

    private void loggingError(String errorPlace, Exception e) {
        LOGGER.error(String.format("%s :: %s", errorPlace, e.getMessage()));
    }

}
