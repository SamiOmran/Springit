package com.exalt.reddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping(path = "/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping(path = "/auth/login")
    public String log() {
        return "auth/login";
    }

    @GetMapping(path = "/auth/profile")
    public String profile() {
        return "auth/profile";
    }

    @GetMapping(path = "/auth/register")
    public String register() {
        return "auth/register";
    }
}
