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
    private Map<String, User> users = new HashMap<>();

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository, RoleRepository roleRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        addUsersRoles();

        //   Adding links to the Database
//        Map<String,String> links = new HashMap<>();
//        links.put("Title 1", "https://www.youtube.com/watch?v=23ruEfLScnM");
//        links.put("Title 2", "https://www.youtube.com/watch?v=23ruEfLScnM");
//        links.put("Title 3", "https://www.youtube.com/watch?v=23ruEfLScnM");
//
//        links.forEach((title, url) -> {
//            User user1 = users.get("sami@gmail.com");
//            User user2 = users.get("master@gmail.com");
//            Link link = new Link(title, url);
//
//            if (title.startsWith("first")) {
//                link.setUser(user1);
//            } else {
//                link.setUser(user2);
//            }
//
//            linkRepository.save(link);
//
//            Comment[] comments = {new Comment("Nice major") ,new Comment("kfmfdkmbkbmgsbgsfbgfbgfs"), new Comment("12345678965") };
//            for (Comment comment:comments) {
//                comment.setLink(link);
//                commentRepository.save(comment);
//                link.addComment(comment);
//            }
//        });
//
//        int linkCount = (int) linkRepository.count();
//        System.out.println("Number of links posted " + linkCount);
    }

    private void addUsersRoles() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepository.save(userRole);
        User user = new User("sami@gmail.com", secret, secret, true, "Sami", "Imran","sane");
        user.addRole(userRole);
        userRepository.save(user);
        users.put("sami@gmail.com",user);

        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);
        User admin = new User("admin@gmail.com", secret, secret, true, "admin", "admin", "admin");
        admin.addRole(adminRole);
        userRepository.save(admin);
        users.put("admin@gmail.com",admin);

        User master = new User("master@gmail.com", secret, secret, true, "master", "master","master");
        master.addRoles(new HashSet<>(Arrays.asList(userRole, adminRole)));
        userRepository.save(master);
        users.put("master@gmail.com", master);

        Long numberOfUsers = userRepository.count();
        System.out.println(numberOfUsers + " users have just inserted in the system");
    }

}
