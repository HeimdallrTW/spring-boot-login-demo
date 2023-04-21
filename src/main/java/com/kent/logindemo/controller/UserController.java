package com.kent.logindemo.controller;

import java.util.HashMap;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kent.logindemo.model.bean.LoginRequest;
import com.kent.logindemo.model.bean.RegisterRequest;
import com.kent.logindemo.model.bean.User;
import com.kent.logindemo.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // test for find user by id
    @GetMapping(path = "/findUserById/{id}")
    @ResponseBody
    public User findUserById(@PathVariable("id") int id) {
        return userService.findUserById(id).get();
    }
    
    @PostMapping(path = "/login")
    public String login(@RequestParam HashMap<String, String> loginRequestMap, RedirectAttributes redirectAttributes, HttpSession session) {
        String email = loginRequestMap.get("email");
        String password = loginRequestMap.get("password");
        LoginRequest loginRequest = new LoginRequest(email, password);
        Optional<User> optional = userService.login(loginRequest);
        if (optional.isEmpty()) {
            redirectAttributes.addFlashAttribute("emailOrPasswordError", "帳號或密碼錯誤");
            return "redirect:/login";
        }
        User user = optional.get();
        if (user.isEnabled() == false) {
            redirectAttributes.addFlashAttribute("accountIsNotEnable", "帳號尚未啟用");
            return "redirect:/login";
        }
        session.setAttribute("user", user);
        return "redirect:/";
    }    

    @GetMapping(path = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping(path = "/register")
    @ResponseBody
    public User register(@RequestBody RegisterRequest registerRequest) {
        System.out.println("registerRequest: " + registerRequest);
        Optional<User> register = userService.register(registerRequest);
        if (register.isPresent()) {
            System.out.println("register success");
            User user = register.get();
            userService.sendVerificationEmail(user);
            return register.get();
        }
        String errorMessage = "register failed, " + registerRequest.email() + ", " + registerRequest.password();
        System.out.println(errorMessage);
        return null;
    }

    @GetMapping(path = "/verify")
    @ResponseBody
    public String verifyEmail(@RequestParam("token") String token, @RequestParam("email") String email) {
        System.out.println("email: " + email + "verified");
        userService.enableAccout(email, token);
        return "驗證成功";
    }

}
