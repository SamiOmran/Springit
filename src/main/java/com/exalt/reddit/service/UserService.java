package com.exalt.reddit.service;

import com.exalt.reddit.model.User;
import com.exalt.reddit.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    private final Logger logger;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private RoleService roleService;

    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        logger = LoggerFactory.getLogger(UserService.class);
        encoder = new BCryptPasswordEncoder();
        this.roleService = roleService;
    }

    public User register(User user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setConfirmPassword(secret);

        user.addRole(roleService.findByName("ROLE_USER"));

        user.setEnabled(true);

        save(user);

        sendActivationEmail(user);

        return user;
    }

    private void sendActivationEmail(User user) {

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void saveUsers(User... users) {
        for (User user : users) {
            logger.info("saving user " + user.getUsername());
            userRepository.save(user);
        }
    }
}
