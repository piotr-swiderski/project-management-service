package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;

    String NEW_PROJECT_PAGES = "newProject";

    @GetMapping("/newProject")
    public String newProject() {
        return NEW_PROJECT_PAGES;
    }

    @PostMapping("/newProject")
    public String newProjectForm(@RequestParam String name, @RequestParam String description, @AuthenticationPrincipal User user) {
        Project project = new Project(name, description, user);
        projectRepository.save(project);

        return "index";
    }

}
