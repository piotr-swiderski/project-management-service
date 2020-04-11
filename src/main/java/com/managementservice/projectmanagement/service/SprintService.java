package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.SprintRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.List;

@Service
public class SprintService {

    private SprintRepository sprintRepository;
    private UserService userService;

    public SprintService(SprintRepository sprintRepository, UserService userService) {
        this.sprintRepository = sprintRepository;
        this.userService = userService;
    }

    public Sprint saveSprint(Sprint sprint){
        return sprintRepository.save(sprint);
    }

    public Sprint getSprintById(Long sprintId) {
        return sprintRepository.findById(sprintId).orElseThrow(NoResultException::new);
    }


    public boolean isUserHaveAccess(Authentication authentication, Long sprintId) {

        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow(NoResultException::new);
        List<User> projectUserList = sprint.getProject().getUsers();
        User user = userService.getUserByAuthentication(authentication);
        return projectUserList.stream().anyMatch(u -> u.getId() == user.getId());
    }
}
