package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.entity.MessageEntity;
import com.studnnet.notification_system.component.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/v1/ns/message")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/")
    public List<MessageEntity> getMessages() {
        return messageRepository.findAll();
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


}

