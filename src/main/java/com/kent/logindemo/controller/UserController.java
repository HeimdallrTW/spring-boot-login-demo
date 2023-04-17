package com.kent.logindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kent.logindemo.model.User;
import com.kent.logindemo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public User getUserById(@PathVariable(name = "id") int id) throws Exception {
        return userService.findUserById(id).orElseThrow(
            () -> new Exception("User with ID: " + id + " not found")
        );
    }
    
}
