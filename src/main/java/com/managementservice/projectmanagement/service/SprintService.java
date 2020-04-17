package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.User;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface SprintService {

    Sprint saveSprint(Sprint sprint);

    Sprint getSprintById(Long sprintId);

    boolean isUserHaveAccess(Authentication authentication, Long sprintId);

    List<User> getUsersFromSprint(Sprint sprint);
}

