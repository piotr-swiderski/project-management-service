package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private UserService userService;

    @Autowired
    public IndexController(UserService userService) {
        this.userService = userService;
    }

    public static final String INDEX_PAGE = "index";

    @GetMapping("/")
    public String getDashboardPage(Model model) {
        model.addAttribute("projects", userService.getAllProjectToUser(userService.getUserAuthentication()));
        return INDEX_PAGE;
    }

    @GetMapping("/index")
    public String getIndexPage(Model model) {
        model.addAttribute("projects", userService.getAllProjectToUser(userService.getUserAuthentication()));
        return INDEX_PAGE;
    }

}
