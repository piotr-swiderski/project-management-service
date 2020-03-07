package com.managementservice.projectmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    public static final String LOGIN_PAGE = "login";

    @GetMapping("/login")
    public String getLoginPage(){
        return LOGIN_PAGE;
    }

}
