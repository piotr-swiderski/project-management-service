package com.managementservice.projectmanagement.controller;


import com.managementservice.projectmanagement.service.NotificationService;
import com.managementservice.projectmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class NotificationController {

    private NotificationService notificationService;
    private UserService userService;


    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @GetMapping("/notification")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void httpStatus() {
        notificationService.saveAll(
                notificationService.setTrueNotificationViewed(
                        notificationService.getListByUserAndVievedNotification(userService.getUserAuthenticationUserName())));
    }

    @GetMapping("/accept")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void acepted() {
        System.out.println("accept");
    }

    @GetMapping("/delete")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void delete() {
        System.out.println("delete");
    }

}
