package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.repositorie.ProjectRepository;
import com.managementservice.projectmanagement.repositorie.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private SprintRepository sprintRepository;

    @Autowired
    public ProjectService(SprintRepository sprintRepository, ProjectRepository projectRepository) {
        this.sprintRepository = sprintRepository;
        this.projectRepository = projectRepository;
    }

    @Transactional
    public void addSprintToProject(long projectId, LocalDate dateTo, LocalDate dateFrom, int storyPoints) {
        Sprint sprint = new Sprint(dateTo, dateFrom, storyPoints);
        Project project = projectRepository.findById(projectId).orElseThrow(NoResultException::new);
        project.addSprints(sprint);

        sprintRepository.save(sprint);
        projectRepository.save(project);
    }

    public Set<Sprint> getListOfProjectSprints(long projectId){
        Project project = projectRepository.findById(projectId).orElseThrow(NoResultException::new);
        return project.getSprints();
    }

    public Project getProject(long projectId) {
        return projectRepository.findById(projectId).orElseThrow(NoResultException::new);
    }
}
