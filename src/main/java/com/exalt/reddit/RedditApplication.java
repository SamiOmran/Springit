package com.exalt.reddit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedditApplication {

    public static void main(String[] args) {
        System.out.println("Welcome to this branch");
        SpringApplication.run(RedditApplication.class, args);
    }

}
