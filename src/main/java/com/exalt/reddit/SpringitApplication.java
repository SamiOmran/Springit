package com.exalt.reddit;
import com.exalt.reddit.model.Comment;
import com.exalt.reddit.model.Link;
import com.exalt.reddit.repositories.CommentRepository;
import com.exalt.reddit.repositories.LinkRepository;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SpringitApplication {

    public static void main(String[] args) {
        System.out.println("Welcome!");
        SpringApplication.run(SpringitApplication.class, args);
    }

    @Bean
    PrettyTime prettyTime() {
        return new PrettyTime();
    }

//    @Bean
//    CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository) {
//        return args -> {
//            Link link = new Link("Getting started SPRING BOOT 2", "https://spring.io/projects/spring-boot");
//            linkRepository.save(link);
//
//            Comment comment = new Comment("I started learning this course & i'm doing well", link);
//            commentRepository.save(comment);
//        };
//    }



}
