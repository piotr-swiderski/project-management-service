package com.managementservice.projectmanagement.service.impl;

import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.SprintRepository;
import com.managementservice.projectmanagement.service.SprintService;
import com.managementservice.projectmanagement.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class SprintServiceImpl implements SprintService {

    private SprintRepository sprintRepository;
    private UserService userService;

    public SprintServiceImpl(SprintRepository sprintRepository, UserService userService) {
        this.sprintRepository = sprintRepository;
        this.userService = userService;
    }

    @Override
    public Sprint saveSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    @Override
    public Sprint getSprintById(Long sprintId) {
        Optional<Sprint> optionalSprint = sprintRepository.findById(sprintId);
        return optionalSprint.orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public boolean isUserHaveAccess(Authentication authentication, Long sprintId) {

        Sprint sprint = getSprintById(sprintId);
        List<User> projectUserList = getUsersFromSprint(sprint);
        User user = userService.getUserByAuthentication(authentication);
        return projectUserList.stream().anyMatch(u -> u.getId() == user.getId());
    }

    @Override
    public List<User> getUsersFromSprint(Sprint sprint) {
        return sprint.getProject().getUsers();
    }
}
