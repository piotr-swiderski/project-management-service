package com.managementservice.projectmanagement.repositorie;

import com.managementservice.projectmanagement.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserUsernameAndStatus(String Username, boolean status);

    List<Notification> findAllByUserUsernameAndViewed(String UserName, boolean viewed);


}

