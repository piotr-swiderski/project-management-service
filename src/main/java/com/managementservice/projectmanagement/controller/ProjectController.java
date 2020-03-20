package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Notification;
import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.service.NotificationService;
import com.managementservice.projectmanagement.service.ProjectService;
import com.managementservice.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

import static com.managementservice.projectmanagement.utils.ControllerUtil.*;

@Controller
public class ProjectController {


    private ProjectService projectService;
    private NotificationService notificationService;
    private UserService userService;


    @Autowired
    public ProjectController(ProjectService projectService, NotificationService notificationService, UserService userService) {
        this.projectService = projectService;
        this.notificationService = notificationService;
        this.userService = userService;
    }


    @GetMapping("/myProjectList")
    public String myProjectList(Model model) {
        model.addAttribute("projects", projectService.getAListOfAllUserNameProjects(userService.getUserAuthentication().getUsername()));
        return "myProjectList";
    }


    @GetMapping("/addUsersToProject")
    public String addUserToProject(Model model,
                                   @RequestParam String projectId) {
        if (projectId != null) {
            model.addAttribute(PROJECT_HANDLER, projectService.getProject(Long.parseLong(projectId)));
        }
        return "addUsersToProject";

    }

    @PostMapping("/addUsersToProject")
    public String addUserToProjectPost(Model model,
                                       @RequestParam String projectId,
                                       @RequestParam String email
    ) {

        model.addAttribute(PROJECT_HANDLER, projectService.getProject(Long.parseLong(projectId)));


        Optional<User> userOptional = userService.getUserByEmail(email);
        Optional<Project> projectOptional = projectService.getProjectById(Long.parseLong(projectId));

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                Notification notification = new Notification("Project invitation", "User "
                        + userService.getUserAuthentication().getEmail()
                        + " I want to add you to the project "
                        + project.getName(), user, project.getId());
                notificationService.save(notification);
                model.addAttribute(SUCCSES_ADDING_NOTIFICATION, SUCCSES_ADDING_NOTIFICATION_MESSAGE);
                return "addUsersToProject";
            }
            model.addAttribute(ERROR_ADDING_NOTIFICATION, ERROR_ADDING_NOTIFICATION_MESSAGE);
        }
        model.addAttribute(ERROR_ADDING_NOTIFICATION_USERS, ERROR_ADDING_NOTIFICATION_USERS_MESSAGE);
        return "addUsersToProject";
    }


    @GetMapping("/viewUsersToProject")
    public String viewUsersToProject(Model model) {
        return "viewUsersToProject";
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
                            @RequestParam String name,
                            @RequestParam String dateFrom,
                            @RequestParam String dateTo,
                            @RequestParam String storyPoints) {

        LocalDate dateFromParse = LocalDate.parse(dateFrom);
        LocalDate dateToParse = LocalDate.parse(dateTo);
        long projectIdParse = Long.parseLong(projectId);
        int storyPointsParse = Integer.parseInt(storyPoints);

        if (!projectService.addSprintToProject(projectIdParse, name, dateFromParse, dateToParse, storyPointsParse)) {
            model.addAttribute(ERROR_ADDING_SPRINT_HANDLER, ERROR_ADDING_SPRINT_MESSAGE);
        }
        model.addAttribute(PROJECT_HANDLER, projectService.getProject(Long.parseLong(projectId)));

        return "projectPage";
    }
}
