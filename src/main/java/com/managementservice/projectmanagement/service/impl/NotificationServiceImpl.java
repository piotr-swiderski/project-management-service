package com.managementservice.projectmanagement.service.impl;

import com.managementservice.projectmanagement.entity.Notification;
import com.managementservice.projectmanagement.repositorie.NotificationRepository;
import com.managementservice.projectmanagement.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    //potrzebuje do skryptu , jeszcze nie wiem jak to ogarnąć inaczej :(
    //TODO
    @Autowired
    private NotificationService notificationService;

    private NotificationRepository notificationRepository;


    public NotificationServiceImpl(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;

    }

    //potrzebuje do skryptu , jeszcze nie wiem jak to ogarnąć inaczej :(
    //TODO
    @Override
    public int counterNotificationViewed(String userName) {
        List<Notification> notificationList = notificationRepository.findAllByUserUsernameAndViewed(userName, false);
        return notificationList.size();
    }

    @Override
    public List<Notification> getListByUserAndVievedNotification(String userName) {
        return notificationRepository.findAllByUserUsernameAndViewed(userName, false);
    }

    @Override
    public List<Notification> setTrueNotificationViewed(List<Notification> notificationList) {
        for (Notification notification : notificationList) {
            notification.setViewed(true);
        }
        return notificationList;
    }

    @Override
    public void saveAll(List<Notification> notificationList) {
        notificationRepository.saveAll(notificationList);
    }

    @Override
    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    @Override
    public Notification getNotificationById(Long id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            return notificationRepository.findById(id).get();
        }
        throw new IllegalArgumentException("Inncorect Notification ID");
    }

    @Override
    public void setStatusToFalseByNotifiaction(Notification notifiaction) {
        notifiaction.setStatus(false);
        notificationRepository.save(notifiaction);
    }

}
