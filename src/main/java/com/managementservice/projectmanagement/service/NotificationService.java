package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Notification;
import com.managementservice.projectmanagement.repositorie.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {


    @Autowired
    NotificationService notificationService;

    @Autowired
    NotificationRepository notificationRepository;

    public int counterNotificationViewed(String userName) {
        List<Notification> notificationList = notificationRepository.findAllByUserUsernameAndViewed(userName, false);
        return notificationList.size();
    }
}
