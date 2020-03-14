package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.ProjectRepository;
import com.managementservice.projectmanagement.repositorie.UserRepository;
import com.managementservice.projectmanagement.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

import static com.managementservice.projectmanagement.utils.ControllerUtil.*;

@Controller
public class ProjectController {

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private ProjectService projectService;

    @Autowired
    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectService = projectService;
    }

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
        return userRepository.findByUsername(userName).get();
    }

    @GetMapping("/projectPage")
    public String getProjectPanel(Model model, @RequestParam(required = false) String projectId) {
        if (projectId != null) {
            model.addAttribute(PROJECT_HANDLER, projectService.getProject(Long.parseLong(projectId)));
        }
        return "projectPage";
    }

    @PostMapping("/projectPage")
    public String addSprint(Model model,
                            @RequestParam String projectId,
                            @RequestParam String dateFrom,
                            @RequestParam String dateTo,
                            @RequestParam String storyPoints) {

        LocalDate dateFromParse = LocalDate.parse(dateFrom);
        LocalDate dateToParse = LocalDate.parse(dateTo);
        long projectIdParse = Long.parseLong(projectId);
        int storyPointsParse = Integer.parseInt(storyPoints);

        if(!projectService.addSprintToProject(projectIdParse, dateFromParse, dateToParse, storyPointsParse)){
            model.addAttribute(ERROR_ADDING_SPRINT_HANDLER, ERROR_ADDING_SPRINT_MESSAGE);
        }
        model.addAttribute(PROJECT_HANDLER, projectService.getProject(Long.parseLong(projectId)));

        return "projectPage";
    }
}
