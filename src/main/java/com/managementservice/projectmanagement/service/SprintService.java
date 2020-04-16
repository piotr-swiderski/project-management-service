package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.SprintRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
public class SprintService {

    private SprintRepository sprintRepository;
    private UserService userService;

    public SprintService(SprintRepository sprintRepository, UserService userService) {
        this.sprintRepository = sprintRepository;
        this.userService = userService;
    }

    public Sprint saveSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public Sprint getSprintById(Long sprintId) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(sprintId);
        Sprint sprint = optionalSprint.orElseThrow(EntityNotFoundException::new);
        return sprint;
    }


    public boolean isUserHaveAccess(Authentication authentication, Long sprintId) {

        Sprint sprint = getSprintById(sprintId);
        List<User> projectUserList = getUsersFromSprint(sprint);
        User user = userService.getUserByAuthentication(authentication);
        return projectUserList.stream().anyMatch(u -> u.getId() == user.getId());
    }

    public List<User> getUsersFromSprint(Sprint sprint) {
        List<User> users = sprint.getProject().getUsers();
        return users;
    }
}
