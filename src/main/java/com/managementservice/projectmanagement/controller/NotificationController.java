package com.managementservice.projectmanagement.controller;


import com.managementservice.projectmanagement.entity.Notification;
import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.service.impl.NotificationServiceImpl;
import com.managementservice.projectmanagement.service.impl.ProjectServiceImpl;
import com.managementservice.projectmanagement.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class NotificationController {

    private NotificationServiceImpl notificationService;
    private UserServiceImpl userService;
    private ProjectServiceImpl projectService;


    public NotificationController(NotificationServiceImpl notificationService, UserServiceImpl userService, ProjectServiceImpl projectService1) {
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
        Project project = projectService.getProjectById(notification.getProjectId());
        User user = userService.getUserFromContext();

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
