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
}
