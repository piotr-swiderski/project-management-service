package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.repositorie.ProjectRepository;
import com.managementservice.projectmanagement.repositorie.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Set;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private SprintRepository sprintRepository;
    private UserService userService;

    @Autowired
    public ProjectService(SprintRepository sprintRepository, ProjectRepository projectRepository, UserService userService) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    @Transactional
    public boolean addSprintToProject(long projectId, String sprintName, LocalDate dateFrom, LocalDate dateTo, int storyPoints) {
        Sprint sprint = new Sprint(dateTo, dateFrom, storyPoints, sprintName);
        Project project = projectRepository.findById(projectId).orElseThrow(NoResultException::new);

        if (!isSprintDateValid(project, dateFrom, dateTo)) {
            return false;
        }
        project.addSprints(sprint);
        sprintRepository.save(sprint);
        projectRepository.save(project);
        return true;
    }

    public Set<Sprint> getListOfProjectSprints(long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(NoResultException::new);
        return project.getSprints();
    }

    public Project getProject(long projectId) {
        return projectRepository.findById(projectId).orElseThrow(NoResultException::new);
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

    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    public Set<Project> getAListOfAllUserNameProjects(String userName) {
        return projectRepository.findAllByAdmin_Username(userName);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElseThrow(NoResultException::new);
    }


}
