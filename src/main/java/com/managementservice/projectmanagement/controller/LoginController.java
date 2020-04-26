package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.service.UserService;
import com.managementservice.projectmanagement.utils.AccountTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.managementservice.projectmanagement.utils.ControllerUtil.*;

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

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute(ERROR_LOGIN_BAD_CREDENSIALS, ERROR_LOGIN_BAD_CREDENSIALS_MESSAGE);
        return LOGIN_PAGE;
    }

    @GetMapping("/login-error-oauth2")
    public String loginErrorOauth2(Model model) {
        model.addAttribute(ERROR_LOGIN_BAD_CREDENSIALS, ERROR_LOGIN_OAUTH2_BAD_CREDENSIALS_MESSAGE);
        return LOGIN_PAGE;
    }


    @PostMapping("/signup")
    public String singUp(@RequestParam(value = "username") String username,
                         @RequestParam(value = "email") String email,
                         @RequestParam(value = "password") String password) {

        userService.registerUser(username, password, email, AccountTypeEnum.NONE);
        return LOGIN_PAGE;
    }

}
