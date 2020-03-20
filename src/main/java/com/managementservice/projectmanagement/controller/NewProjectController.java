package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.ParticipationInTheProject;
import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.service.ParticipationInTheProjectService;
import com.managementservice.projectmanagement.service.ProjectService;
import com.managementservice.projectmanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewProjectController {

    private ProjectService projectService;
    private UserService userService;
    private ParticipationInTheProjectService participationService;

    public NewProjectController(ProjectService projectService, UserService userService, ParticipationInTheProjectService participationService) {
        this.projectService = projectService;
        this.userService = userService;
        this.participationService = participationService;
    }

    @GetMapping("/newProject")
    public String newProject() {
        return "newProject";
    }


    @PostMapping("/newProject")
    public String newProjectForm(Model model, @RequestParam String name, @RequestParam String description) {
        Project project = new Project(name, description, userService.getUserAuthentication());
        projectService.saveProject(project);
        ParticipationInTheProject participation = new ParticipationInTheProject(project, userService.getUserAuthentication());
        participationService.save(participation);

        model.addAttribute("projects", projectService.getAListOfAllUserNameProjects(userService.getUserAuthentication().getUsername()));
        return "/myProjectList";
    }

}
