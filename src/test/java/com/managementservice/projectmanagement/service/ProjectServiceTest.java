package com.managementservice.projectmanagement.service;

import com.managementservice.projectmanagement.entity.Project;
import com.managementservice.projectmanagement.entity.Sprint;
import com.managementservice.projectmanagement.entity.User;
import com.managementservice.projectmanagement.repositorie.ProjectRepository;
import com.managementservice.projectmanagement.service.impl.ProjectServiceImpl;
import com.managementservice.projectmanagement.service.impl.SprintServiceImpl;
import com.managementservice.projectmanagement.service.impl.UserServiceImpl;
import com.managementservice.projectmanagement.utils.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.managementservice.projectmanagement.utils.TestUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @Mock
    ProjectRepository projectRepository;
    @Mock
    SprintServiceImpl sprintService;
    @Mock
    UserServiceImpl userService;
    @Mock
    Authentication authentication;
    @InjectMocks
    ProjectServiceImpl projectService;


    @Test
    public void addSprintToProject_shouldAddSprintToProject() {
        //given
        Project project = TestUtil.getProject();
        LocalDate dateFrom = LocalDate.of(2020, 1, 10);
        LocalDate dateTo = LocalDate.of(2020, 1, 20);
        int storyPoint = 100;
        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        given(sprintService.saveSprint(any(Sprint.class))).willReturn(new Sprint());
        given(projectRepository.save(any(Project.class))).willReturn(new Project());
        //when
        boolean isSprintAdd = projectService.addSprintToProject(PROJECT_ID, SPRINT_NAME, dateFrom, dateTo, storyPoint);
        //then
        assertThat(isSprintAdd).isTrue();
    }

    @Test
    public void addSprintToProject_shouldNotAddSprintToProjectConflictWithAnotherSprint() {
        //given
        Project project = TestUtil.getProject();
        LocalDate dateFrom = LocalDate.of(2019, 1, 8);
        LocalDate dateTo = LocalDate.of(2019, 1, 22);
        int storyPoint = 100;
        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        //when
        boolean isSprintAdd = projectService.addSprintToProject(PROJECT_ID, SPRINT_NAME, dateFrom, dateTo, storyPoint);
        //then
        assertThat(isSprintAdd).isFalse();
    }

    @Test
    public void addSprintToProject_shouldNotAddSprintToProjectWrongDate() {
        //given
        Project project = TestUtil.getProject();
        LocalDate dateTo = LocalDate.of(2019, 1, 8);
        LocalDate dateFrom = LocalDate.of(2019, 1, 22);
        int storyPoint = 100;
        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        //when
        boolean isSprintAdd = projectService.addSprintToProject(PROJECT_ID, SPRINT_NAME, dateFrom, dateTo, storyPoint);
        //then
        assertThat(isSprintAdd).isFalse();
    }

    @Test
    public void getListOfProjectSprints_shouldReturnListOfSprints() {
        //given
        List<Sprint> listOfFourSprints = getListOfFourSprints();
        Project project = getProject();
        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        //when
        Set<Sprint> returnedSprintsList = projectService.getListOfProjectSprints(PROJECT_ID);
        //then
        assertThat(returnedSprintsList.size()).isEqualTo(4);
    }

    @Test
    public void getProject_shouldReturnAProjectById() {
        //given
        Project project = TestUtil.getProject();
        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        //when
        Project returnedProject = projectService.getProjectById(PROJECT_ID);
        //then
        assertThat(returnedProject).isEqualTo(project);
    }

    @Test
    public void saveProject_shouldSaveProject() {
        //given
        Project project = getProject();
        given(projectRepository.save(project)).willReturn(project);
        //when
        Project saveProject = projectService.saveProject(project);
        //then
        assertThat(saveProject).isSameAs(project);
    }

    @Test
    public void getAListOfAllUserNameProjects_shouldReturnUserProjectsByUsername() {
        //given
        Set<Project> sprints = new HashSet<>(getListOfFourProjects());
        given(projectRepository.findAllByAdmin_Username(USER_USERNAME)).willReturn(sprints);
        //when
        Set<Project> returnedProjects = projectService.getAListOfAllUserNameProjects(USER_USERNAME);
        //then
        assertThat(returnedProjects.size()).isEqualTo(4);
    }

    @Test
    public void isUserHaveAccess_shouldReturnTrue() {
        //given
        User user = getUser();
        Project project = getProject();
        given(userService.getUserByAuthentication(authentication)).willReturn(user);
        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        // when
        boolean access = projectService.isUserHaveAccess(authentication, PROJECT_ID);
        //then
        assertThat(access).isTrue();
    }

    @Test
    public void isUserHaveAccess_shouldReturnFalse() {
        //given
        User user = new User();
        Project project = getProject();
        given(userService.getUserByAuthentication(authentication)).willReturn(user);
        given(projectRepository.findById(PROJECT_ID)).willReturn(Optional.of(project));
        // when
        boolean access = projectService.isUserHaveAccess(authentication, PROJECT_ID);
        //then
        assertThat(access).isTrue();
    }
}
