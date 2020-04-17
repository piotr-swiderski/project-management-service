package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.Sprint;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Set;

public interface ProjectService {

    boolean addSprintToProject(long projectId, String sprintName, LocalDate dateFrom, LocalDate dateTo, int storyPoints);

    Set<Sprint> getListOfProjectSprints(long projectId);

    Project getProjectById(long projectId);

    Project saveProject(Project project);

    Set<Project> getAListOfAllUserNameProjects(String userName);

    boolean isUserHaveAccess(Authentication authentication, long projectId);

}
