package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Notification;

import java.util.List;

public interface NotificationService {

    int counterNotificationViewed(String userName);

    List<Notification> getListByUserAndVievedNotification(String userName);

    List<Notification> setTrueNotificationViewed(List<Notification> notificationList);

    void saveAll(List<Notification> notificationList);

    void save(Notification notification);

    Notification getNotificationById(Long id);

    void setStatusToFalseByNotifiaction(Notification notifiaction);

}
