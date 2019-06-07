package com.studnnet.notification_system.component.repositories;

import com.studnnet.notification_system.component.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    private UserEntity testUser;

    @Autowired
    private UserRepository userRepository;


    @Test
    public void existsByUsernameAndPasswordFailedIfUserIsNotExist() {
        createUser();
        try {
            assertTrue("Should be true", userRepository.
                existsByUsernameAndPassword(testUser.getUsername(),
                    testUser.getPassword()));
        } finally {
            userRepository.delete(testUser.getId());
        }

    }

    @Test
    public void saveUserFailedIfUserIsNotSaved() {
        assertEquals(createUser(), testUser);
    }

    @Test
    public void getUserFailedIfDontGetUser() throws Exception {
        createUser();
        assertEquals(testUser, userRepository.findOne(testUser.getId()));
    }




    private UserEntity createUser() {
        testUser = new UserEntity();
        testUser.setUsername("testUsername");
        testUser.setPassword("testPassword");

        return userRepository.save(testUser);
    }
}