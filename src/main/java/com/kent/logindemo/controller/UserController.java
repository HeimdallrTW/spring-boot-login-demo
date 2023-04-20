package com.kent.logindemo.controller;

import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kent.logindemo.model.bean.LoginRequest;
import com.kent.logindemo.model.bean.User;
import com.kent.logindemo.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    // receive post request and do login then redirect to index page
    @PostMapping(path = "/login")
    public String login(@RequestParam HashMap<String, String> loginRequestMap,  HttpSession session) {
        String email = loginRequestMap.get("email");
        String password = loginRequestMap.get("password");
        LoginRequest loginRequest = new LoginRequest(email, password);
        System.out.println("loginRequest: " + loginRequest);
        Optional<User> optional = userService.login(loginRequest);
        if (optional.isPresent()) {
            System.out.println("login success");
            User user = optional.get();
            session.setAttribute("user", user);
            return "redirect:/";
        }
        System.out.println("login failed");
        return "redirect:/login";
    }    

    // logout
    @GetMapping(path = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
