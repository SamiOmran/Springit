package com.exalt.reddit.controller;

import com.exalt.reddit.model.User;
import com.exalt.reddit.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    UserService userService;
    private final Logger logger;

    public AuthController(UserService userService) {
        this.userService = userService;
        logger = LoggerFactory.getLogger(AuthController.class);
    }

    @GetMapping(path = "/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping(path = "/profile")
    public String profile() {
        return "auth/profile";
    }

    @GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("Couldn't register new user");
            model.addAttribute("user", user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "auth/register";
        } else {
            User newUser = userService.register(user);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success", true);
            logger.info("Success registering new user");
            return "redirect:/register";
        }
    }
}
