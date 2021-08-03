package com.exalt.reddit.service;

import com.exalt.reddit.model.User;
import com.exalt.reddit.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(User user) {
        return user;
    }

    public User save(User user) {
        return user;
    }

    @Transactional
    public void saveUsers(User... users) {
        for (User user : users) {
            logger.info("saving user " + user.getUsername());
            userRepository.save(user);
        }
    }
}
