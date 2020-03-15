package com.managementservice.projectmanagement;

import com.managementservice.projectmanagement.repositorie.NotificationRepository;
import com.managementservice.projectmanagement.repositorie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectManagementApplication {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(ProjectManagementApplication.class, args);
    }

}
