package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.entity.MessageEntity;
import com.studnnet.notification_system.component.repositories.MessageRepository;
import com.studnnet.notification_system.utils.enums.MailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/v1/ns/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public List<MessageEntity> getMessagesWithoutBlackList() {
        return messageRepository.findAllWithoutBlackList(MailStatus.BLACK_LIST);
    }

    @GetMapping("/{userId}")
    public List<MessageEntity> getMessages(@PathParam("userId")Integer userId) {
        return messageRepository.findAll();
    }

    @GetMapping("/{id}")
    public MessageEntity getMessageById(@PathVariable("id") Integer id) {
        return messageRepository.findOne(id);
    }

    @PutMapping("/")
    public MessageEntity createMessage(@RequestBody MessageEntity userEntity) {
        return messageRepository.save(userEntity);
    }

    @PostMapping("/")
    public MessageEntity updateMessage(@RequestBody MessageEntity userEntity) {
        return messageRepository.save(userEntity);
    }

    @PutMapping("/all")
    public List<MessageEntity> createMessages(@RequestBody List<MessageEntity> messageEntities){
        return messageRepository.save(messageEntities);
    }

    @PostMapping("/blacklist")
    @Transactional
    public void addToBlackList(@RequestBody List<String> emails){

        messageRepository.updateMailStatus(emails, MailStatus.BLACK_LIST);
    }

    @GetMapping("/blacklist")
    public List<MessageEntity> getMessagesBlackList() {
        return messageRepository.findByStatus(MailStatus.BLACK_LIST);
    }

    @GetMapping("/{from}/{to}")
    public List<MessageEntity> getMessagesList(@PathVariable("from") long from,
                                                    @PathVariable("to") long to) {



        return  messageRepository.findByDateFromTo(MailStatus.BLACK_LIST, new Date(from), new Date(to));
    }

    @PostMapping("/blacklist/remove")
    @Transactional
    public void removeFromBlackList(@RequestBody List<String> emails){

        messageRepository.updateMailStatus(emails, MailStatus.NEW);
    }

    @GetMapping("/top")
    public List<MessageEntity> getMessagesTop() {

        return  messageRepository.findTop(MailStatus.BLACK_LIST, new java.util.Date().getMonth()+1);
    }
}

