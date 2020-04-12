package com.managementservice.projectmanagement.controller;


import com.managementservice.projectmanagement.entity.Notification;
import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.service.NotificationService;
import com.managementservice.projectmanagement.service.ProjectService;
import com.managementservice.projectmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class NotificationController {

    private NotificationService notificationService;
    private UserService userService;
    private ProjectService projectService;


    public NotificationController(NotificationService notificationService, UserService userService, ProjectService projectService1) {
        this.notificationService = notificationService;
        this.userService = userService;
        this.projectService = projectService1;

    }

    @GetMapping("/notification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void httpStatus() {
        notificationService.saveAll(
                notificationService.setTrueNotificationViewed(
                        notificationService.getListByUserAndVievedNotification(userService.getUsernameFromAuthentication())));
    }

    @GetMapping("/accept")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void acepted(@RequestParam String id) {


        Notification notification = notificationService.getNotificationById(Long.parseLong(id));
        Project project = projectService.getProject(notification.getProjectId());
        User user = userService.getUserByAuthentication();

        project.addUser(user);
        projectService.saveProject(project);


        notificationService.setStatusToFalseByNotifiaction(notification);
    }


    @GetMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete(@RequestParam String id) {
        Notification notification = notificationService.getNotificationById(Long.parseLong(id));
        notificationService.setStatusToFalseByNotifiaction(notification);
    }

}
