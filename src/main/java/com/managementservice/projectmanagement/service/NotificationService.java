package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Notification;
import com.managementservice.projectmanagement.repositorie.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    //potrzebuje do skryptu , jeszcze nie wiem jak to ogarnąć inaczej :(
    //TODO
    @Autowired
    private NotificationService notificationService;

    private NotificationRepository notificationRepository;


    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;

    }


    //potrzebuje do skryptu , jeszcze nie wiem jak to ogarnąć inaczej :(
    //TODO
    public int counterNotificationViewed(String userName) {
        List<Notification> notificationList = notificationRepository.findAllByUserUsernameAndViewed(userName, false);
        return notificationList.size();
    }


    public List<Notification> getListByUserAndVievedNotification(String userName) {
        return notificationRepository.findAllByUserUsernameAndViewed(userName, false);
    }

    public List<Notification> setTrueNotificationViewed(List<Notification> notificationList) {
        for (Notification notification : notificationList) {
            notification.setViewed(true);
        }
        return notificationList;
    }

    public void saveAll(List<Notification> notificationList) {
        notificationRepository.saveAll(notificationList);
    }

    public void save(Notification notification) {
        notificationRepository.save(notification);
    }

    public Notification getNotificationById(Long id) {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            return notificationRepository.findById(id).get();
        }
        throw new IllegalArgumentException("Inncorect Notification ID");
    }

    public void setStatusToFalseByNotifiaction(Notification notifiaction) {
        notifiaction.setStatus(false);
        notificationRepository.save(notifiaction);
    }

}
