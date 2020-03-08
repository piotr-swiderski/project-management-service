package com.managementservice.projectmanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping ("project")
public class ProjectController {

    String NEW_PROJECT_PAGES = "newProject";

    @GetMapping("/newProject")
    public String newProject () {
        return NEW_PROJECT_PAGES;
    }

}
