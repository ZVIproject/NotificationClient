package com.studnnet.notification_system.controller;

import com.studnnet.notification_system.component.entity.UserEntity;
import com.studnnet.notification_system.component.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/v1/ns/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public List<UserEntity> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public UserEntity getUserById(@PathVariable("id") Integer id){
        return userRepository.findOne(id);
    }

    @PutMapping("/")
    public UserEntity createUser(@RequestBody UserEntity userEntity){
        //TODO z: add functionality for check user in the database and then save it if exist
        return userRepository.save(userEntity);
    }

    @PostMapping("/")
    public UserEntity updateUser(@RequestBody UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    @PostMapping("/authorization")
    public UserEntity checkUser(@RequestBody UserEntity userEntity){
        return userRepository.findByUsernameAndPassword(userEntity.getUsername(), userEntity.getPassword());
    }
}
