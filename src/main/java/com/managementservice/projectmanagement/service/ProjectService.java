package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private SprintService sprintService;
    private UserService userService;

    @Autowired
    public ProjectService(SprintService sprintService, ProjectRepository projectRepository, UserService userService) {
        this.sprintService = sprintService;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }


    public boolean addSprintToProject(long projectId, String sprintName, LocalDate dateFrom, LocalDate dateTo, int storyPoints) {
        Project project = projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
        Sprint sprint = new Sprint(dateTo, dateFrom, storyPoints, sprintName, project);

        if (!isSprintDateValid(project, dateFrom, dateTo)) {
            return false;
        }
        project.addSprints(sprint);
        sprintService.saveSprint(sprint);
        projectRepository.save(project);
        return true;
    }


    public Set<Sprint> getListOfProjectSprints(long projectId) {
        Project project = getProjectById(projectId);
        return project.getSprints();
    }

    public Project getProjectById(long projectId) {
        return projectRepository.findById(projectId).orElseThrow(EntityNotFoundException::new);
    }

    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    public Set<Project> getAListOfAllUserNameProjects(String userName) {
        return projectRepository.findAllByAdmin_Username(userName);
    }


    public boolean isUserHaveAccess(Authentication authentication, long projectId) {
        Project project = getProjectById(projectId);
        User user = userService.getUserByAuthentication(authentication);
        List<User> projectUsers = project.getUsers();

        return projectUsers.stream()
                .anyMatch(
                        u -> u.getId() ==  user.getId()
                );
    }

    private boolean isSprintDateValid(Project project, LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.isAfter(dateTo)) {
            return false;
        }
        for (Sprint s : project.getSprints()) {
            if (s.getDateTo().isAfter(dateFrom)) {
                return false;
            }
        }
        return true;
    }
}
