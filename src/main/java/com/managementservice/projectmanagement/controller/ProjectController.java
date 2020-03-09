package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.ProjectRepository;
import com.managementservice.projectmanagement.repositorie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProjectController {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserRepository userRepository;


    @GetMapping("/newProject")
    public String newProject() {
        return "newProject";
    }

    @PostMapping("/newProject")
    public String newProjectForm(@RequestParam String name, @RequestParam String description) {
        Project project = new Project(name, description, getUserAuthentication());
        projectRepository.save(project);

        return "index";
    }

    @GetMapping("/myProjectList")
    public String myProjectList(Model model) {
        model.addAttribute("projects", projectRepository.findAllByAdmin_Username(getUserAuthentication().getUsername()));
        return "myProjectList";
    }


    public User getUserAuthentication() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userRepository.findByUsername(userName);
    }


}
