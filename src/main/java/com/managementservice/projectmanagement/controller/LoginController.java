package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private UserService userService;

    public static final String LOGIN_PAGE = "login";

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return LOGIN_PAGE;
    }

    @PostMapping("/signup")
    public String singUp(@RequestParam(value = "username") String username,
                         @RequestParam(value = "email") String email,
                         @RequestParam(value = "password") String password) {

        userService.registerUser(username, password, email);
        return LOGIN_PAGE;
    }

}
