package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.service.impl.ProjectServiceImpl;
import com.managementservice.projectmanagement.service.impl.UserServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewProjectController {

    private ProjectServiceImpl projectService;
    private UserServiceImpl userService;


    public NewProjectController(ProjectServiceImpl projectService, UserServiceImpl userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/newProject")
    public String newProject() {
        return "newProject";
    }


    @PostMapping("/newProject")
    public String newProjectForm(Model model, @RequestParam String name, @RequestParam String description) {
        User user = userService.getUserFromContext();
        Project project = new Project(name, description, user);

        project.addUser(user);
        projectService.saveProject(project);
        userService.save(user);

        model.addAttribute("projects", projectService.getAListOfAllUserNameProjects(userService.getUserFromContext().getUsername()));
        return "index";
    }

}
