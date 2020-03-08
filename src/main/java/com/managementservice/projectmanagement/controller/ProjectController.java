package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.ProjectRepository;
import com.managementservice.projectmanagement.repositorie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    String NEW_PROJECT_PAGES = "newProject";
    String LIST_PROJECT_PAGES = "myProjectList";

    @GetMapping("/newProject")
    public String newProject() {
        return NEW_PROJECT_PAGES;
    }

    @PostMapping("/newProject")
    public String newProjectForm(@RequestParam String name, @RequestParam String description) {
        Project project = new Project(name, description, getUserAuthentication());
        projectRepository.save(project);

     return "index";
    }

    @GetMapping ("/myProject")
    public String myProjectList (@RequestParam String username, Model model) {
        model.addAttribute("projects", projectRepository.findAllByUsers(getUserAuthentication().getUsername()) );
        return LIST_PROJECT_PAGES;
    }


    public User getUserAuthentication () {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return  userRepository.findByUsername(userName);
    }




}
