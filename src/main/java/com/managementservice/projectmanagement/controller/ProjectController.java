package com.managementservice.projectmanagement.controller;

import com.managementservice.projectmanagement.entity.Notification;
import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.service.NotificationService;
import com.managementservice.projectmanagement.service.ProjectService;
import com.managementservice.projectmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.List;

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
        model.addAttribute("projects", projectService.getAListOfAllUserNameProjects(userService.getUserFromContext().getUsername()));
        return "myProjectList";
    }


    @GetMapping("/addUsersToProject")
    public String addUserToProject(Model model,
                                   @RequestParam long projectId) {

        model.addAttribute(PROJECT_HANDLER, projectService.getProjectById(projectId));

        return "addUsersToProject";

    }

    @PostMapping("/addUsersToProject")
    public String addUserToProjectPost(Model model,
                                       @RequestParam long projectId,
                                       @RequestParam String email
    ) {

        model.addAttribute(PROJECT_HANDLER, projectService.getProjectById(projectId));

        User user;
        Project project;

        try {
            user = userService.getUserByEmail(email);
        } catch (NoResultException e) {
            model.addAttribute(ERROR_ADDING_NOTIFICATION_USERS, ERROR_ADDING_NOTIFICATION_USERS_MESSAGE);
            return "addUsersToProject";
        }

        try {
            project = projectService.getProjectById(projectId);
        } catch (NoResultException e) {
            model.addAttribute(SUCCSES_ADDING_NOTIFICATION, SUCCSES_ADDING_NOTIFICATION_MESSAGE);
            return "addUsersToProject";
        }

        Notification notification = new Notification("Project invitation", "User "
                + userService.getUserFromContext().getEmail()
                + " I want to add you to the project "
                + project.getName(), user, project.getId());
        notificationService.save(notification);
        model.addAttribute(SUCCSES_ADDING_NOTIFICATION, SUCCSES_ADDING_NOTIFICATION_MESSAGE);
        return "addUsersToProject";

    }


    @GetMapping("/viewUsersToProject")
    public String viewUsersToProject(@RequestParam String projectId, Model model) {

        long parseProjectId = Long.parseLong(projectId);
        Project project = projectService.getProjectById(parseProjectId);
        List<User> userList = project.getUsers();
        model.addAttribute("users", userList);
        model.addAttribute("project", project);
        return "viewUsersToProject";

    }


    @GetMapping("/deleteUser")
    public String deleteUSer(@RequestParam String projectId, @RequestParam String userId, Model model) {
        User user;
        Project project;


        try {
            user = userService.getUserById(Long.parseLong(userId));
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            //TODO
            return "viewUsersToProject";
        }

        try {
            project = projectService.getProjectById(Long.parseLong(projectId));
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            //TODO
            return "viewUsersToProject";
        }


        if (user == project.getAdmin()) {
            model.addAttribute(ERROR_DELETE_USERS_TO_PROJECT, ERROR_DELETE_USERS_TO_PROJECT_MESSAGE);
            return viewUsersToProject(projectId, model);
        }

        project.getUsers().remove(user);
        projectService.saveProject(project);
        model.addAttribute(SUCCSES_DELETE_USER, SUCCSES_DELETE_USER_MESSAGE);

        return viewUsersToProject(projectId, model);
    }


    @GetMapping("/projectPage")
    public String getProjectPanel(Authentication authentication, Model model, @RequestParam(required = false) long projectId) {



        if (!projectService.isUserHaveAccess(authentication, projectId)) {
            model.addAttribute(ERROR_HANDLER, ERROR_MSG_ACCESS);
            model.addAttribute(ERROR_HELP_HANDLER, ERROR_MSG_HELP_ACCESS);
            return "errorPage";
        }

        model.addAttribute(PROJECT_HANDLER, projectService.getProjectById(projectId));
        return "projectPage";
    }

    @PostMapping("/projectPage")
    public String addSprint(Model model,
                            @RequestParam long projectId,
                            @RequestParam String name,
                            @RequestParam LocalDate dateFrom,
                            @RequestParam LocalDate dateTo,
                            @RequestParam String storyPoints) {

        int storyPointsParse = Integer.parseInt(storyPoints);

        if (!projectService.addSprintToProject(projectId, name, dateFrom, dateTo, storyPointsParse)) {
            model.addAttribute(ERROR_ADDING_SPRINT_HANDLER, ERROR_ADDING_SPRINT_MESSAGE);
        }

        model.addAttribute(PROJECT_HANDLER, projectService.getProjectById(projectId));
        return "projectPage";
    }
}
