package com.exalt.reddit.bootstrap;

import com.exalt.reddit.model.Comment;
import com.exalt.reddit.model.Link;
import com.exalt.reddit.model.Role;
import com.exalt.reddit.model.User;
import com.exalt.reddit.repositories.CommentRepository;
import com.exalt.reddit.repositories.LinkRepository;
import com.exalt.reddit.repositories.RoleRepository;
import com.exalt.reddit.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private final LinkRepository linkRepository;
    private final CommentRepository commentRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        //Adding users with roles to Database

        addUsersRoles();

        //   Adding links to the Database
        Map<String, String> links = new HashMap<>();
        links.put("first link goes to facebook", "https://facebook.com");
        links.put("second link goes to twitter", "https://twitter.com");

        links.forEach((title, url) -> {
            Link link = new Link(title, url);
            linkRepository.save(link);

            Comment c1 = new Comment("This link is good", link);
            commentRepository.save(c1);
            link.addComment(c1);

            int linkCount = (int) linkRepository.count();
            System.out.println("Number of links in database is " + linkCount);
        });
    }

    private void addUsersRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        User user = new User("sami@gmail.com", secret, true);
        user.addRole(userRole);
        userRepository.save(user);

        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);
        User admin = new User("admin@gmail.com", secret, true);
        admin.addRole(adminRole);
        userRepository.save(admin);

        User master = new User("master@gmail.com", secret, true);
        master.addRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        userRepository.save(master);

        Long numberOfUsers = userRepository.count();
        System.out.println(numberOfUsers + " users have just inserted to the database");

    }

}
