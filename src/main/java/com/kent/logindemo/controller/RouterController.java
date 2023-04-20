package com.kent.logindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {

    @GetMapping({ "/", "/index" })
    public String index() {
        return "index";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "/user/login";
    }

}
