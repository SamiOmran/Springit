package com.exalt.reddit.bootstrap;

import com.exalt.reddit.model.Link;
import com.exalt.reddit.repositories.CommentRepository;
import com.exalt.reddit.repositories.LinkRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {
    private LinkRepository linkRepository;
    private CommentRepository commentRepository;

    public DatabaseLoader(LinkRepository linkRepository, CommentRepository commentRepository) {
        this.linkRepository = linkRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public void run(String... args) {
        Map<String,String> links = new HashMap<>();

        links.put("first link goes to facebook", "https://facebook.com");
        links.put("second link goes to twitter", "https://twitter.com");

        links.forEach((title, url) -> {
            linkRepository.save(new Link(title, url));
        });

        int linkCount = (int) linkRepository.count();
        System.out.println("Number of links in database is " + linkCount);
    }
}
