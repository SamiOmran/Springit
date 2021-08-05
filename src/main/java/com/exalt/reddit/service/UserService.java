package com.exalt.reddit.service;

import com.exalt.reddit.model.User;
import com.exalt.reddit.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final Logger logger;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private RoleService roleService;
    private MailService mailService;

    public UserService(UserRepository userRepository, RoleService roleService, MailService mailService) {
        this.userRepository = userRepository;
        logger = LoggerFactory.getLogger(UserService.class);
        encoder = new BCryptPasswordEncoder();
        this.roleService = roleService;
        this.mailService = mailService;
    }

    public User register(User user) {
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setConfirmPassword(secret);

        user.addRole(roleService.findByName("ROLE_USER"));

        user.setActivationCode(UUID.randomUUID().toString());

        user.setEnabled(false);

        save(user);

        sendActivationEmail(user);

        return user;
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

    private void sendActivationEmail(User user) {
        mailService.sendActivationEmail(user);
    }

    public void sendWelcomeEmail(User user) {
        mailService.sendWelcomeEmail(user);
    }

    public Optional<User> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepository.findByEmailAndActivationCode(email, activationCode);
    }
}
